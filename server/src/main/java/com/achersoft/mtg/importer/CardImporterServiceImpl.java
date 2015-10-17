package com.achersoft.mtg.importer;

import com.achersoft.mtg.importer.dao.Card;
import com.achersoft.mtg.importer.dao.Sets;
import com.achersoft.mtg.importer.persistence.ImporterMapper;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CardImporterServiceImpl implements CardImporterService {
    
    private @Inject ImporterMapper mapper;
    
    @Override
    public void importSets(Sets sets) {
        sets.getSets().stream().filter((set) -> !set.onlineOnly).forEach((set) -> {
            set.setId(DigestUtils.sha1Hex(set.name));
            mapper.addSet(set);
            set.getCards().stream().map((card) -> {
                if(card.names != null) {
                    Card splitCard = new Card();
                    splitCard.setId(DigestUtils.sha1Hex(String.join("", card.names)));
                    splitCard.setSetId(set.getId());
                    if(card.getType().contains("Instant") || card.getType().contains("Sorcery")) {
                    if(card.names.size() <= 2)
                        splitCard.setName(String.join(" // ", card.names));
                    else
                        splitCard.setName(String.join("/", card.names));
                    } else
                        splitCard.setName(String.join(" | ", card.names));
                    card.setSplitSequence(card.names.indexOf(card.name)+1);
                    card.setSplitId(splitCard.getId());
                    mapper.addCard(splitCard);
                }
                card.setSetId(set.getId());
                return card;
            }).forEach((card) -> {
                mapper.addCard(card);
            });
        });
    }
}