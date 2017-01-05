package com.achersoft.mtg.card;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.CardDetails;
import com.achersoft.mtg.card.dao.CardListItem;
import com.achersoft.mtg.card.dao.CardSearch;
import com.achersoft.mtg.card.dao.SearchList;
import com.achersoft.mtg.card.dao.Set;
import java.util.List;

public interface CardService {
    public void addInventory(List<Card> cards);
    public void adjustInventory(List<Card> cards);
    public CardDetails getCard(String id);
    public List<Set> getSets(String language);
    public List<CardListItem> getSet(String id, String language);
    public SearchList search(CardSearch search);
    public List<Card> getSetInventory(String id, String language);
    public void refreshSets();
}