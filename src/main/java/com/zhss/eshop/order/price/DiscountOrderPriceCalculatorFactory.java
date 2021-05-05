package com.zhss.eshop.order.price;

import com.zhss.eshop.promotion.constant.PromotionActivityType;
import com.zhss.eshop.promotion.domain.PromotionActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 折扣减免型的订单价格计算组件工厂
 * @author zhonghuashishan
 *
 */
@Component
public class DiscountOrderPriceCalculatorFactory implements OrderPriceCalculatorFactory {


	private final Map<Integer, PromotionActivityCalculator> calculatorMap = new ConcurrentHashMap<>();


	/**
	 * 默认的总金额计算组件
	 */
	@Autowired
	private DefaultTotalPriceCalculator totalPriceCalculator;
//	/**
//	 * 满减类型的促销活动计算组件
//	 */
//	@Autowired
//	private ReachDiscountPromotionActivityCalculator reachDiscountPromotionActivityCalculator;
//	/**
//	 * 多买优惠型的促销活动计算组件
//	 */
//	@Autowired
//	private MultiDiscountPromotionActivityCalculator multiDiscountPromotionActivityCalculator;
//	/**
//	 * 单品促销型的促销活动计算组件
//	 */
//	@Autowired
//	private DirectDiscountPromotionActivityCalcualtor directDiscountPromotionActivityCalcualtor;
	/**
	 * 默认的促销活动计算组件
	 */
	@Autowired
	private DefaultPromotionActivityCalculator defaultPromotionActivityCalculator;
	/**
	 * 默认的运费计算组件
	 */
	@Autowired
	private DefaultFreightCalculator freightCalculator;


	@Autowired
	public DiscountOrderPriceCalculatorFactory(ReachDiscountPromotionActivityCalculator reachDiscountPromotionActivityCalculator,
											   MultiDiscountPromotionActivityCalculator multiDiscountPromotionActivityCalculator,
											   DirectDiscountPromotionActivityCalcualtor directDiscountPromotionActivityCalcualtor) {
		calculatorMap.put(PromotionActivityType.REACH_DISCOUNT, reachDiscountPromotionActivityCalculator);
		calculatorMap.put(PromotionActivityType.MULTI_DISCOUNT, multiDiscountPromotionActivityCalculator);
		calculatorMap.put(PromotionActivityType.DIRECT_DISCOUNT, directDiscountPromotionActivityCalcualtor);

	}


	
	/**
	 * 创建总金额计算组件
	 */
	@Override
	public TotalPriceCalculator createTotalPriceCalculator() {
		return totalPriceCalculator;
	}


	/**
	 * 创建促销活动计算组件
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
	 * 创建运费计算组件
	 */
	@Override
	public FreightCalculator createFreightCalculator() {
		return freightCalculator;
	}

}
