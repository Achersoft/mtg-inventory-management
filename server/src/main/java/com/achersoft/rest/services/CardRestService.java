package com.achersoft.rest.services;

import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.card.dao.Card;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.dto.CardDTO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cards")
public class CardRestService {

    private @Inject CardService cardService; 
    //private @Inject ApplicationContext ctx;

    @GET 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public CardDTO getCard(@PathParam("id") String id) throws Exception {
        return CardDTO.fromDAO(cardService.getCard(id));
    }
    
    @GET 
    @Path("/sets/")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<Set> getSets(@QueryParam("language") String language) throws Exception {
        return cardService.getSets(language);	
    }

    @GET 
    @Path("/sets/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<CardDTO> getSet(@PathParam("id") String id, @QueryParam("language") String language) throws Exception {
        return cardService.getSet(id, language).stream().map((dao) -> {return CardDTO.fromDAO(dao);}).collect(Collectors.toList());	
    }
    
    @GET 
    @Path("/test/")
    @Produces("image/png")	
    public Response getSetss() throws Exception {
        URL url = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=240017&type=card");
        BufferedImage image = ImageIO.read(url);
        File f = new File(System.getProperty("catalina.base"), "webapps/static/images");
        if(!f.exists())
            f.mkdirs();
        File f2 = new File(f, "dfdbdb.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", f2);
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();

        // uncomment line below to send non-streamed
        return Response.ok(imageData).build();

        // uncomment line below to send streamed
        // return Response.ok(new ByteArrayInputStream(imageData)).build();

       // return cardService.getSets(language);	
    }
}

