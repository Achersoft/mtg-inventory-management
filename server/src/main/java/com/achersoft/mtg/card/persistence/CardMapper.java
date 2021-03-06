package com.achersoft.mtg.card.persistence;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.CardSearch;
import com.achersoft.mtg.card.dao.Set;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CardMapper {
    public Card getCard(String id);
    public List<Card> getAdditionalDetails(@Param("id") String id);
    public List<Card> getAdditionalPrintings(@Param("id") String id, @Param("name") String name);
    public List<Set> getSets(String language);
    public List<Card> getSet(@Param("id") String id, @Param("language") String language);
    public List<Card> getSetInventory(@Param("id") String id, @Param("language") String language);
    public List<Card> search(CardSearch search);
    public int searchCount(CardSearch search);
    public void addInventory(Card card);
    public void adjustInventory(Card card);
}