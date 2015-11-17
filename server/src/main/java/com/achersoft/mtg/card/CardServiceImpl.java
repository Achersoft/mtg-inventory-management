package com.achersoft.mtg.card;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import java.util.List;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CardServiceImpl implements CardService {

    private @Inject CardMapper mapper;
    
    @Override
    public List<Set> getSets() {
        return mapper.getSets();
    }    

    @Override
    public List<Card> getSet() {
        return mapper.getSet();
    }

}