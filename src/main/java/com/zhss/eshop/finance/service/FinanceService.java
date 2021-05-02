package com.zhss.eshop.finance.service;

import com.zhss.eshop.wms.domain.PurchaseInputOrderDTO;
import com.zhss.eshop.wms.domain.SaleDeliveryOrderDTO;

/**
 * 财务中心对外提供的接口
 * @author zhonghuashishan
 *
 */
public interface FinanceService {

	/**
	 * 创建采购结算单
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	Boolean createPurchaseSettlementOrder(PurchaseInputOrderDTO purchaseInputOrder);
	
	/**
	 * 给物流公司打款
	 * @param saleDeliveryOrderDTO 销售出库单
	 * @return 处理结果
	 */
	Boolean payForLogisticsCompany(SaleDeliveryOrderDTO saleDeliveryOrder);
	
}
