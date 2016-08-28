package com.achersoft.rest.services;

import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dto.CardDTO;
import com.achersoft.mtg.card.dto.CardListItemDTO;
import com.achersoft.mtg.card.dto.SetDTO;
import com.achersoft.security.annotations.RequiresPrivilege;
import com.achersoft.security.type.Privilege;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/cards")
public class CardRestService {

    private @Inject CardService cardService; 

    @GET 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public CardDTO getCard(@PathParam("id") String id) throws Exception {
        return CardDTO.fromDAO(cardService.getCard(id));
    }
    
    @GET 
    @Path("/sets/")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<SetDTO> getSets(@QueryParam("language") String language) throws Exception {
        List<SetDTO> sets = new ArrayList();
        cardService.getSets(language).stream().forEach((set) -> {
            sets.add(SetDTO.fromDAO(set));
        });
        return sets;	
    }

    @GET 
    @Path("/sets/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<CardListItemDTO> getSet(@PathParam("id") String id, @QueryParam("language") String language) throws Exception {
        return cardService.getSet(id, language).stream().map((dao) -> {return CardListItemDTO.fromDAO(dao);}).collect(Collectors.toList());	
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.EMPLOYEE})
    @GET 
    @Path("/setinventory/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<Card> getSetInventory(@PathParam("id") String id, @QueryParam("language") String language, @QueryParam("clearQty") boolean clearQty) throws Exception {
        if(clearQty)
            return cardService.getSetInventory(id, language).stream().map((card) -> {
                card.FHP = 0;
                card.FMP = 0;
                card.FSP = 0;
                card.FNM = 0;
                card.HP = 0;
                card.MP = 0;
                card.SP = 0;
                card.NM = 0;
                return card;
            }).collect(Collectors.toList());
        return cardService.getSetInventory(id, language);	
    }
    
    @GET 
    @Path("/search")
    @Consumes({MediaType.APPLICATION_JSON})	
    @Produces({MediaType.APPLICATION_JSON})	
    public List<CardListItemDTO> search(@QueryParam("name") String name) throws Exception {
	return cardService.search(name).stream().map((dao) -> {return CardListItemDTO.fromDAO(dao);}).collect(Collectors.toList());
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.EMPLOYEE})
    @PUT 
    @Path("/setinventory")
    @Consumes({MediaType.APPLICATION_JSON})	
    public void addInventory(List<Card> cards) throws Exception {
	cardService.addInventory(cards);
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.EMPLOYEE})
    @PUT 
    @Path("/adjustinventory")
    @Consumes({MediaType.APPLICATION_JSON})	
    public void adjustInventory(List<Card> cards) throws Exception {
	cardService.adjustInventory(cards);
    }
}

