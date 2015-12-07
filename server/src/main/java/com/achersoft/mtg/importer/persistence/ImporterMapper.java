package com.achersoft.mtg.importer.persistence;

import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.SetImport;

public interface ImporterMapper {
    public void addSet(SetImport set);
    public void addCard(CardImport card);
    public void addCardToInventory(CardImport card);
}