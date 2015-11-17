package com.achersoft.rest.services;

import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.dto.CardDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cards")
public class CardRestService {

    private @Inject CardService cardService; 
    //private @Inject ApplicationContext ctx;

    @GET 
    @Path("/sets")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<Set> getSets() throws Exception {
        return cardService.getSets();	
    }

    @GET 
    @Produces({MediaType.APPLICATION_JSON})	
    public List<CardDTO> getSet() throws Exception {
        List<CardDTO> collect = cardService.getSet().stream().map((dao) -> {return CardDTO.fromDAO(dao);}).collect(Collectors.toList());	
        System.err.println(collect);
        return collect;
    }
}