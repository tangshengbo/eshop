package com.zhss.eshop.commodity.state;

import com.zhss.eshop.commodity.constant.GoodsStatus;
import com.zhss.eshop.commodity.dao.GoodsDAO;
import com.zhss.eshop.commodity.domain.GoodsDO;
import com.zhss.eshop.commodity.domain.GoodsDTO;
import com.zhss.eshop.common.util.DateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 热门商品
 */
@Component
public class HotGoodsState implements GoodsState {

    /**
     * 日期辅助组件
     */
    @Autowired
    private DateProvider dateProvider;
    /**
     * 商品管理DAO组件
     */
    @Autowired
    private GoodsDAO goodsDAO;

    @Override
    public void doTransition(GoodsDTO goods) throws Exception {
        goods.setStatus(GoodsStatus.GOODS_HOT);
        goods.setGmtModified(dateProvider.getCurrentTime());
        goodsDAO.updateStatus(goods.clone(GoodsDO.class));
    }

    @Override
    public Boolean canEdit(GoodsDTO goods) throws Exception {
        return false;
    }

    @Override
    public Boolean canApprove(GoodsDTO goods) throws Exception {
        return false;
    }

    @Override
    public Boolean canPutOnShelves(GoodsDTO goods) throws Exception {
        return false;
    }

    @Override
    public Boolean canPullOffShelves(GoodsDTO goods) throws Exception {
        return true;
    }

    @Override
    public Boolean canRemove(GoodsDTO goods) throws Exception {
        return false;
    }

    @Override
    public Boolean canHotGoods(GoodsDTO goods) {
        return true;
    }

}
