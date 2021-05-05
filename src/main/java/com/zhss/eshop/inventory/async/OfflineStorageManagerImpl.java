package com.zhss.eshop.inventory.async;

import com.alibaba.fastjson.JSONObject;
import com.zhss.eshop.common.util.DateProvider;
import com.zhss.eshop.inventory.dao.StockUpdateMessageDAO;
import com.zhss.eshop.inventory.domain.StockUpdateMessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 离线存储管理组件
 * @author zhonghuashishan
 *
 */
@Component
public class OfflineStorageManagerImpl implements OfflineStorageManager {
	
	/**
	 * 库存更新消息管理模块DAO组件
	 */
	@Autowired
	private StockUpdateMessageDAO stockUpdateMessageDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 是否触发离线存储的标识
	 */
	private Boolean offline = false;
	
	/**
	 * 离线存储库存更新消息
	 * @param message 库存更新消息
	 * @throws Exception
	 */
	@Override
	public void store(StockUpdateMessage message) throws Exception {
		StockUpdateMessageDO stockUpdateMessageDO = createStockUpdateMessageDO(message);
		stockUpdateMessageDAO.save(stockUpdateMessageDO);
	}
	
	/**
	 * 创建库存更新消息DO对象
	 * @param message 库存更新消息
	 * @return 库存更新消息DO对象
	 * @throws Exception
	 */
	private StockUpdateMessageDO createStockUpdateMessageDO(
			StockUpdateMessage message) throws Exception {
		StockUpdateMessageDO stockUpdateMessageDO = new StockUpdateMessageDO();
		stockUpdateMessageDO.setMessageId(message.getId());
		stockUpdateMessageDO.setOperation(message.getOperation());
		stockUpdateMessageDO.setParameter(JSONObject.toJSONString(message.getParameter())); 
		stockUpdateMessageDO.setParameterClazz(message.getParameter().getClass().getName());
		stockUpdateMessageDO.setGmtCreate(dateProvider.getCurrentTime()); 
		stockUpdateMessageDO.setGmtModified(dateProvider.getCurrentTime()); 
		return stockUpdateMessageDO;
	}

	/**
	 * 获取离线存储标识
	 * @return 离线存储标识
	 * @throws Exception
	 */
	@Override
	public Boolean getOffline() throws Exception {
		return offline;
	}
	
	/**
	 * 设置离线存储标识
	 * @param offline 离线存储标识
	 * @throws Exception
	 */
	@Override
	public void setOffline(Boolean offline) throws Exception {
		this.offline = offline;
	}
	
	/**
	 * 批量删除库存更新消息
	 * @param stockUpdateMessages 库存更新消息
	 * @throws Exception
	 */
	@Override
	public void removeByBatch(List<StockUpdateMessage> stockUpdateMessages) throws Exception {
		String ids = stockUpdateMessages.stream().map(StockUpdateMessage::getId).collect(Collectors.joining(","));
		stockUpdateMessageDAO.removeByBatch(ids);
	}
	
	/**
	 * 获取离线数据迭代器
	 * @throws Exception
	 */
	@Override
	public OfflineStorageIterator iterator() throws Exception {
		return new OfflineStorageIteratorImpl();
	}
	
	/**
	 * 离线数据迭代器
	 * @author zhonghuashishan
	 *
	 */
	public class OfflineStorageIteratorImpl implements OfflineStorageIterator {
		
		/**
		 * 判断是否还有下一批库存更新消息
		 * @return 是否还有下一批库存更新消息
		 * @throws Exception
		 */
		@Override
		public Boolean hasNext() throws Exception {
			return !stockUpdateMessageDAO.count().equals(0L);
		}
		
		/**
		 * 获取下一批库存更新消息
		 * @return 下一批库存更新消息
		 * @throws Exception
		 */
		@Override
		public List<StockUpdateMessage> next() throws Exception {
			List<StockUpdateMessage> stockUpdateMessages = new ArrayList<StockUpdateMessage>();
			
			List<StockUpdateMessageDO> stockUpdateMessageDOs = 
					stockUpdateMessageDAO.listByBatch();
			for(StockUpdateMessageDO stockUpdateMessageDO : stockUpdateMessageDOs) {
				StockUpdateMessage stockUpdateMessage = new StockUpdateMessage();
				stockUpdateMessage.setId(stockUpdateMessageDO.getMessageId()); 
				stockUpdateMessage.setOperation(stockUpdateMessageDO.getOperation()); 
				stockUpdateMessage.setParameter(JSONObject.parseObject(stockUpdateMessageDO.getParameter(), 
						Class.forName(stockUpdateMessageDO.getParameterClazz())));
				stockUpdateMessages.add(stockUpdateMessage);
			}
			
			return stockUpdateMessages;
		}
		
	}
	
}
