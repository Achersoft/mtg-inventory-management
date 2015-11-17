package com.achersoft.mtg.card;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import java.util.List;

public interface CardService {
    public List<Set> getSets();
    public List<Card> getSet();
}