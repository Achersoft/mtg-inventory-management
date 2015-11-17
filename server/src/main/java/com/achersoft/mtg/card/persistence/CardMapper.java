package com.achersoft.mtg.card.persistence;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import java.util.List;

public interface CardMapper {
    public List<Set> getSets();
    public List<Card> getSet();
}