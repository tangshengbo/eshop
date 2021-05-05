package com.zhss.eshop.order.price;

import com.zhss.eshop.promotion.constant.PromotionActivityType;
import com.zhss.eshop.promotion.domain.PromotionActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 赠品类型的订单价格计算组件工厂
 * @author zhonghuashishan
 *
 */
@Component
public class GiftOrderPriceCalculatorFactory implements OrderPriceCalculatorFactory {

	private final Map<Integer, PromotionActivityCalculator> calculatorMap = new ConcurrentHashMap<>();

	/**
	 * 默认的总金额计算组件
	 */
	@Autowired
	private DefaultTotalPriceCalculator totalPriceCalculator;
//	/**
//	 * 满赠类型的促销活动计算组件
//	 */
//	@Autowired
//	private ReachGiftPromotionActivityCalculator reachGiftPromotionActivityCalculator;
//	/**
//	 * 赠品类型的促销活动计算组件
//	 */
//	@Autowired
//	private DirectGiftPromotionActivityCalculator directGiftPromotionActivityCalculator;
	/**
	 * 默认的促销活动计算组件
	 */
	@Autowired
	private DefaultPromotionActivityCalculator defaultPromotionActivityCalculator;
	/**
	 * 包含赠品的运费计算组件
	 */
	@Autowired
	private GiftIncludedFreightCalculator freightCalculator;

	@Autowired
	public GiftOrderPriceCalculatorFactory(ReachGiftPromotionActivityCalculator reachGiftPromotionActivityCalculator,
										   DirectGiftPromotionActivityCalculator directGiftPromotionActivityCalculator) {
		calculatorMap.put(PromotionActivityType.REACH_GIFT, reachGiftPromotionActivityCalculator);
		calculatorMap.put(PromotionActivityType.DIRECT_GIFT, directGiftPromotionActivityCalculator);
	}
	
	/**
	 * 创建订单总金额价格计算组件
	 * @return 订单总金额价格计算组件
	 */
	@Override
	public TotalPriceCalculator createTotalPriceCalculator() {
		return totalPriceCalculator;
	}
	
	/**
	 * 创建促销活动价格计算组件
	 * @return 促销活动价格计算组件
	 */
	@Override
	public PromotionActivityCalculator createPromotionActivityCalculator(
			PromotionActivityDTO promotionActivity) {
		if(promotionActivity == null) {
			return defaultPromotionActivityCalculator;
		}
		Integer promotionActivityType = promotionActivity.getType();
		PromotionActivityCalculator promotionActivityCalculator = calculatorMap.get(promotionActivityType);
		if (Objects.isNull(promotionActivityCalculator)) {
			return defaultPromotionActivityCalculator;
		}
		return promotionActivityCalculator;

	}
	
	/**
	 * 创建运费价格计算组件
	 * @return 运费价格计算组件
	 */
	@Override
	public FreightCalculator createFreightCalculator() {
		return freightCalculator;
	}
	
}
