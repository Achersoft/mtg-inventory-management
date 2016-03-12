package com.achersoft.mtg.price.persistence;

import com.achersoft.mtg.price.dao.PriceSet;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PriceMapper {
    public List<PriceSet> getSets();
    public String getCardId(
            @Param("setId") String setId, 
            @Param("cardName") String cardName,
            @Param("language") String language);
    public void updatePrice(
            @Param("cardId") String cardId, 
            @Param("nm") double nm, 
            @Param("sp") double sp,
            @Param("mp") double mp,
            @Param("hp") double hp,
            @Param("fnm") double fnm,
            @Param("fsp") double fsp,
            @Param("fmp") double fmp,
            @Param("fhp") double fhp);
}