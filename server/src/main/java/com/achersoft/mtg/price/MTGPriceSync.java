package com.achersoft.mtg.price;

import com.achersoft.mtg.price.dao.CardPrice;
import com.achersoft.mtg.price.dao.PriceSet;
import com.achersoft.mtg.price.dao.PriceSetList;
import com.achersoft.mtg.price.persistence.PriceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public class MTGPriceSync {
    private static final String API_KEY = "brianDOTseippATgmailDOTcom-FriJan0804-37-57UTC2016";
    private static final String API_URL = "http://www.mtgprice.com/api";
    private @Inject PriceMapper mapper;
    
    @Scheduled(cron="0 0 * * * *")
    public void sync() {
        for(PriceSet set : mapper.getSets()) {
          //  System.err.println("Get Set: " + set.getCode());
            try {
                for(CardPrice card : getSetPriceList(set.getCode()).getCards()) {
                    String cardId = mapper.getCardId(set.getId(), card.getName(), "English");
                    Double price = Double.valueOf(card.getFairPrice().replace("$", ""));
                    updatePrice(cardId, price);
                }
            } catch (Exception ex) {
                System.err.println("Get Set: " + set.getCode());
          //      Logger.getLogger(MTGPriceSync.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Transactional
    private void updatePrice(String cardId, Double price){
        mapper.updatePrice(cardId, price, price, price, price, price, price, price, price);
    }
    
    private PriceSetList getSetPriceList(String set) throws Exception{
        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        return new ObjectMapper().readValue(client.target(API_URL)
                .queryParam("apiKey", API_KEY)
                .queryParam("s", set)
                .request()
                .accept(MediaType.TEXT_HTML)
                .get()
                .readEntity(String.class), PriceSetList.class);
    }
}


