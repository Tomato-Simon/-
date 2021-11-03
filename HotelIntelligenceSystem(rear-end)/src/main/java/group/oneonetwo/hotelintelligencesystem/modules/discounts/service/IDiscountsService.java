package group.oneonetwo.hotelintelligencesystem.modules.discounts.service;

import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.po.DiscountsPO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;

import java.util.List;

public interface IDiscountsService {
    DiscountsVO add(DiscountsVO discountsVO);
    DiscountsVO selectOneByIdReturnVO(String id);
    DiscountsPO save(DiscountsVO discountsVO);
    Integer deleteById(String id);
    DiscountsPO selectOneById(String id);
    DiscountsVO saveone (DiscountsVO discountsVO);

}

