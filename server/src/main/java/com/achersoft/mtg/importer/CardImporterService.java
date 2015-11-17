package com.achersoft.mtg.importer;

import com.achersoft.mtg.importer.dao.SetsImport;

public interface CardImporterService {
    public void importSets(SetsImport sets);
}