package com.achersoft.mtg.importer;

import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.achersoft.mtg.importer.persistence.ImporterMapper;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CardImporterServiceImpl implements CardImporterService {
    
    private @Inject ImporterMapper mapper;
    
    @Override
    public void importSets(SetsImport sets) {
        sets.getSets().stream().filter((set) -> !set.onlineOnly).forEach((set) -> {
            set.setId(DigestUtils.sha1Hex(set.name));
            mapper.addSet(set);
            set.getCards().stream().filter((card) -> card.layout.equals("normal") || card.layout.equals("split") || 
                    card.layout.equals("flip") || card.layout.equals("double-faced")
            ).map((card) -> {
                if(card.layout.equalsIgnoreCase("split")) {
                    CardImport splitCard = new CardImport();
                    splitCard.setId(DigestUtils.sha1Hex(String.join("", card.names)));
                    splitCard.setSetId(set.getId());
                    splitCard.setName(String.join(" // ", card.names));
                    card.setSplitSequence(card.names.indexOf(card.name)+1);
                    card.setSplitId(splitCard.getId());
                    mapper.addCard(splitCard);
                } else if(card.layout.equalsIgnoreCase("flip") || card.layout.equalsIgnoreCase("double-faced")) {
                    CardImport flipCard = new CardImport();
                    flipCard.setId(DigestUtils.sha1Hex(String.join("", card.names)));
                    flipCard.setSetId(set.getId());
                    flipCard.setName(String.join(" | ", card.names));
                    card.setSplitSequence(card.names.indexOf(card.name)+1);
                    card.setSplitId(flipCard.getId());
                    mapper.addCard(flipCard);
                }
                card.setSetId(set.getId());
                return card;
            }).forEach((card) -> {
                mapper.addCard(card);
            });
        });
    }
}