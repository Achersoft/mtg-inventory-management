package com.achersoft.rest.services;

import com.achersoft.mtg.importer.CardImporterService;
import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.ForeignImport;
import com.achersoft.mtg.importer.dao.SetImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.context.ApplicationContext;

@Path("/importer")
public class ImporterRestService {

    private @Inject CardImporterService cardImporter; 
    //private @Inject ApplicationContext ctx;

    @POST 
    @Path("/upload")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void importAllSets(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws Exception {
        SetsImport sets = new ObjectMapper().readValue(fileInputStream, SetsImport.class);
     //   sets.getSets().stream().forEach((set) -> {System.err.println(set.name);});
        sets.getSets().stream().forEach((set) -> {
            Map vars = new HashMap();

            set.setId(DigestUtils.sha1Hex(set.name));
 //           mapper.addSet(set);
            set.getCards().stream().filter((card) -> card.layout.equals("normal") || card.layout.equals("split") || 
                    card.layout.equals("flip") || card.layout.equals("double-faced")
            ).forEach((card) -> {
                if(card.getForeignNames() == null || card.getForeignNames().isEmpty())
                    card.setForeignNames(new ArrayList());
                card.getForeignNames().add(ForeignImport.builder().language("english").multiverseid(card.multiverseid).build());
                card.getForeignNames().stream().forEach((lang) -> { 
                    if(card.getVariations() != null && !card.getVariations().isEmpty()) {
                        if(card.getNumber() != null)
                            card.setName(card.getName() + " (#" + card.getNumber() + ")" );
                        else {
                            if(!vars.containsKey(card.getName())) 
                                vars.put(card.getName(), "A");
                                
                            card.setName(card.getName() + " (" + vars.get(card.getName()) + ")" );
                            vars.put(card.getName(), vars.get(card.getName()));
                        }
                        card.setSetId(DigestUtils.sha1Hex(card.getName()+set.getId()+lang.getLanguage()));
                        card.setLanguage(lang.getLanguage());
                        card.setMultiverseid(lang.getMultiverseid());
                        System.err.println(card);
 //                       mapper.addCard(card);
                    } });/*else {
                        card.setSetId(DigestUtils.sha1Hex(card.getName()+set.getId()+lang.getLanguage()));
                        card.setLanguage(lang.getLanguage());
                        card.setMultiverseid(lang.getMultiverseid());
                        if(card.layout.equalsIgnoreCase("split")) {
                            CardImport splitCard = new CardImport();
                            splitCard.setId(DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage()));
                            splitCard.setSetId(set.getId());
                            splitCard.setName(String.join(" // ", card.names));
                            splitCard.setHasChildren(true);
                            splitCard.setLanguage(lang.getLanguage());
                            splitCard.setLayout("split");
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            card.setSplitId(splitCard.getId());
  //                          mapper.addCard(splitCard);
                        } else if(card.layout.equalsIgnoreCase("flip") || card.layout.equalsIgnoreCase("double-faced")) {
                            CardImport flipCard = new CardImport();
                            flipCard.setId(DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage()));
                            flipCard.setSetId(set.getId());
                            flipCard.setName(String.join(" | ", card.names));
                            flipCard.setLayout("flip");
                            flipCard.setHasChildren(true);
                            flipCard.setLanguage(lang.getLanguage());
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            card.setSplitId(flipCard.getId());
 //                           mapper.addCard(flipCard);
                        } else {

                        }
  //                      mapper.addCard(card);
                    }
                });*/
            });
        });
     //   cardImporter.importSets(new ObjectMapper().readValue(fileInputStream, SetsImport.class));
    }
}