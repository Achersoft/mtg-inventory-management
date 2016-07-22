package com.achersoft.mtg.price;

import com.achersoft.mtg.price.dao.CardPrice;
import com.achersoft.mtg.price.dao.PriceSetList;
import com.achersoft.mtg.price.persistence.PriceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.scheduling.annotation.Scheduled;

public class MTGPriceSync {
    private static final String API_KEY = "brianDOTseippATgmailDOTcom-FriJan0804-37-57UTC2016";
    private static final String API_URL = "http://www.mtgprice.com/api";
    private @Inject PriceMapper mapper;
    
    @Scheduled(fixedRate=21600000)
    public void sync() {
        updatePrice();
    }

    private void updatePrice(){
        mapper.getSets().stream().forEach((set) -> {
            try {
                for(CardPrice card : getSetPriceList(set.getCode()).getCards()) {
                    String cardId = mapper.getCardId(set.getId(), card.getName(), "English");
                    Double price = Double.valueOf(card.getFairPrice().replace("$", ""));
                    mapper.updatePrice(cardId, price, price, price, price, price, price, price, price);
                }
            } catch (Exception ex) {
                System.err.println("Get Set: " + set.getCode());
            }
        });
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


