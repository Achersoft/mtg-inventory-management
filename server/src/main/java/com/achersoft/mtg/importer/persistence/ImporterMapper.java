package com.achersoft.mtg.importer.persistence;

import com.achersoft.mtg.importer.dao.Card;
import com.achersoft.mtg.importer.dao.Set;

public interface ImporterMapper {
    public void addSet(Set set);
    public void addCard(Card card);
}