package com.zhss.eshop.commodity.state;

import com.zhss.eshop.commodity.constant.GoodsStatus;
import com.zhss.eshop.commodity.domain.GoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商品状态工厂
 * @author zhonghuashishan
 *
 */
@Component
public class GoodsStateFactory {

	private static final Map<Integer, GoodsState> GOODS_STATE_MAP = new ConcurrentHashMap<>();

//	/**
//	 * 待审核状态
//	 */
//	@Autowired
//	private WaitForApproveGoodsState waitForApproveGoodsState;
//	/**
//	 * 待上架状态
//	 */
//	@Autowired
//	private WaitForPutOnShelvesGoodsState waitForPutOnShelvesGoodsState;
//	/**
//	 * 审核未通过状态
//	 */
//	@Autowired
//	private ApproveRejectGoodsState approveRejectGoodsState;
//	/**
//	 * 已上架状态
//	 */
//	@Autowired
//	private PuttedOnShelvesGoodsState puttedOnShelvesGoodsState;
	/**
	 * 默认商品状态
	 */
	@Autowired
	private DefaultGoodsState defaultGoodsState;

	@Autowired
	public GoodsStateFactory(WaitForApproveGoodsState waitForApproveGoodsState,
							 WaitForPutOnShelvesGoodsState waitForPutOnShelvesGoodsState,
							 ApproveRejectGoodsState approveRejectGoodsState,
							 PuttedOnShelvesGoodsState puttedOnShelvesGoodsState,
							 HotGoodsState hotGoodsState) {
		GOODS_STATE_MAP.put(GoodsStatus.WAIT_FOR_APPROVE, waitForApproveGoodsState);
		GOODS_STATE_MAP.put(GoodsStatus.WAIT_FOR_PUT_ON_SHELVES, waitForPutOnShelvesGoodsState);
		GOODS_STATE_MAP.put(GoodsStatus.APPROVE_REJECT, approveRejectGoodsState);
		GOODS_STATE_MAP.put(GoodsStatus.PUTTED_ON_SHELVES, puttedOnShelvesGoodsState);
		GOODS_STATE_MAP.put(GoodsStatus.GOODS_HOT, hotGoodsState);
	}
	
	/**
	 * 获取商品对应的状态组件
	 * @param goods 商品 
	 * @return 状态组件
	 * @throws Exception
	 */
	public GoodsState get(GoodsDTO goods) throws Exception {
		return GOODS_STATE_MAP.getOrDefault(goods.getStatus(), defaultGoodsState);
	}
}
