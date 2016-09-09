package com.achersoft.mtg.card;

import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.CardDetails;
import com.achersoft.mtg.card.dao.CardListItem;
import com.achersoft.mtg.card.dao.CardSearch;
import com.achersoft.mtg.card.dao.CardStock;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class CardServiceImpl implements CardService {

    private @Inject CardMapper mapper;
    private @Resource(name="setList") Map<String, List<Set>> setList;
    
    @Override
    public void addInventory(List<Card> cards) {
       cards.stream().forEach((card) -> {
           mapper.addInventory(card);
       });
    }
    
    @Override
    public void adjustInventory(List<Card> cards) {
       cards.stream().forEach((card) -> {
           mapper.adjustInventory(card);
       });
    }
    
    @Override
    public CardDetails getCard(String id) {
        CardDetails cardDetails = CardDetails.fromCard(mapper.getCard(id));
        mapper.getAdditionalPrintings(id, cardDetails.getName()).forEach((card) -> {
            cardDetails.getAdditionalPrintings().addAll(CardStock.fromDAO(card));
        });
        return cardDetails;
    }
    
    @Override
    public List<Set> getSets(String language) {
        return setList.get(language);
    }    

    @Override
    public List<CardListItem> getSet(String id, String language) {
        return mapper.getSet(id, language).stream().map((card) -> {
            return CardListItem.fromCard(card);
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<CardListItem> search(CardSearch search) {
        return mapper.search(search).stream().map((card) -> {
            return CardListItem.fromCard(card);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Card> getSetInventory(String id, String language) {
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
        setLists.put("Chinese", mapper.getSets("Chinese Simplified"));
        setLists.put("Portuguese", mapper.getSets("Portuguese (Brazil)"));
        setLists.put("French", mapper.getSets("French"));
        setLists.put("Italian", mapper.getSets("Italian"));
        setList = setLists;
    }
}