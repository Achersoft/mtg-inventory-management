package com.achersoft.mtg.card;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CardServiceImpl implements CardService {

    private @Inject CardMapper mapper;
    private @Resource(name="setList") Map<String, List<Set>> setList;
    
    @Override
    public Card getCard(String id) {
        return mapper.getCard(id);
    }
    
    @Override
    public List<Set> getSets(String language) {
        return setList.get(language);
    }    

    @Override
    public List<Card> getSet(String id, String language) {
        return mapper.getSet(id, language);
    }

    @Override
    public void refreshSets() {
        Map<String, List<Set>> setLists = new HashMap();
        setLists.put("English", mapper.getSets("English"));
        setLists.put("Russian", mapper.getSets("Russian"));
        setLists.put("Japanese", mapper.getSets("Japanese"));
        setLists.put("Korean", mapper.getSets("Korean"));
        setLists.put("Spanish", mapper.getSets("Spanish"));
        setLists.put("German", mapper.getSets("German"));
        setLists.put("Portuguese", mapper.getSets("Portuguese (Brazil)"));
        setLists.put("French", mapper.getSets("French"));
        setLists.put("Italian", mapper.getSets("Italian"));
        setList = setLists;
    }
}