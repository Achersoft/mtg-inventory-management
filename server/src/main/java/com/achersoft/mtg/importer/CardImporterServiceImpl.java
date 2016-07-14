package com.achersoft.mtg.importer;

import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.ForeignImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.achersoft.mtg.importer.persistence.ImporterMapper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

public class CardImporterServiceImpl implements CardImporterService, Runnable { 

    private @Inject CardService cardService; 
    private @Inject ImporterMapper mapper;
    private @Inject ThreadPoolExecutor threadPoolExecutor;
    private SetsImport sets;
    
    @Override
    public void importSets(SetsImport sets) {
        if(!threadPoolExecutor.getQueue().contains(this)) {
            this.sets = sets;
            threadPoolExecutor.execute(this);
        }
    }

    @Override
    public void run() {
        importSets();
    }
    
    private void importSets() {
        Logger.getLogger(CardImporterServiceImpl.class.getName()).log(Level.INFO, "Started Card Import");
        sets.getSets().stream().forEach((set) -> {
            Logger.getLogger(CardImporterServiceImpl.class.getName()).log(Level.INFO, "Import Set: {0}", set.getName());
            Map vars = new HashMap();
            set.setId(DigestUtils.sha1Hex(set.name));
            mapper.addSet(set);
            set.getCards().stream().filter((card) -> card.layout.equals("normal") || card.layout.equals("split") || 
                    card.layout.equals("flip") || card.layout.equals("double-faced")
            ).forEach((card) -> {
                card.setSetId(set.getId());
                if(card.getForeignNames() == null || card.getForeignNames().isEmpty())
                    card.setForeignNames(new ArrayList());
                card.getForeignNames().add(ForeignImport.builder().language("English").multiverseid(card.multiverseid).build());
                card.getForeignNames().stream().forEach((lang) -> { 
                    if(card.getVariations() != null && !card.getVariations().isEmpty()) {
                        CardImport cardVariant = card.copy();
                        if(cardVariant.getNumber() != null)
                            cardVariant.setName(cardVariant.getName() + " (#" + cardVariant.getNumber() + ")" );
                        else {
                            if(!vars.containsKey(cardVariant.getName())) 
                                vars.put(cardVariant.getName(), "A");
                            else
                                vars.put(cardVariant.getName(), String.valueOf( (char)(vars.get(cardVariant.getName()).toString().charAt(0) + 1)));
                                
                            cardVariant.setName(cardVariant.getName() + " (" + vars.get(cardVariant.getName()) + ")" );
                        }
                        cardVariant.setId(DigestUtils.sha1Hex(cardVariant.getName()+set.getId()+lang.getLanguage()));
                        cardVariant.setLanguage(lang.getLanguage());
                        cardVariant.setMultiverseid(lang.getMultiverseid());
                        mapper.addCard(cardVariant);
                        mapper.addCardToInventory(cardVariant);
                        importImage(cardVariant.getId(), cardVariant.getMultiverseid());
                    } else {
                        card.setId(DigestUtils.sha1Hex(card.getName()+set.getId()+lang.getLanguage()));
                        card.setLanguage(lang.getLanguage());
                        card.setMultiverseid(lang.getMultiverseid());
                        if(card.layout.equalsIgnoreCase("split")) {
                            CardImport splitCard = CardImport.builder()
                                    .id(DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage()))
                                    .setId(set.getId())
                                    .name(String.join(" // ", card.names))
                                    .hasChildren(true)
                                    .language(lang.getLanguage())
                                    .layout("split")
                                    .build();
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            card.setSplitId(splitCard.getId());
                            mapper.addCard(splitCard);
                            mapper.addCardToInventory(splitCard);
                            importImage(splitCard.getId(), splitCard.getMultiverseid());
                        } else if(card.layout.equalsIgnoreCase("flip") || card.layout.equalsIgnoreCase("double-faced")) {
                            CardImport flipCard = CardImport.builder()
                                    .id(DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage()))
                                    .setId(set.getId())
                                    .name(String.join(" | ", card.names))
                                    .hasChildren(true)
                                    .language(lang.getLanguage())
                                    .layout("flip")
                                    .build();
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            card.setSplitId(flipCard.getId());
                            mapper.addCard(flipCard);
                            mapper.addCardToInventory(flipCard);
                            importImage(flipCard.getId(), flipCard.getMultiverseid());
                        } else {
                            if(card.getOriginalText() == null && card.getText() != null)
                                card.setOriginalText(card.getText());
                            mapper.addCard(card);
                            mapper.addCardToInventory(card);
                            importImage(card.getId(), card.getMultiverseid());
                        }
                        if(card.getOriginalText() == null && card.getText() != null)
                            card.setOriginalText(card.getText());
                        mapper.addCard(card);
                    }
                });
            });
        });
        cardService.refreshSets();
    }
    
    private void importImage(String id, String multiverseId) {
        try {
            File f = new File(System.getProperty("catalina.base"), "webapps/static/images");
                if(!f.exists())
                    f.mkdirs();
            File f2 = new File(f, id);
            if(f2.exists())
                return;
            URL url = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + multiverseId + "&type=card");
            BufferedImage image = ImageIO.read(url);
            if(image != null) {
                ImageIO.write(image, "jpeg", f2);
            } else {
                image = ImageIO.read(new File(new File(System.getProperty("catalina.base"), "webapps/titan/images"), "blank.jpeg"));
                ImageIO.write(image, "jpeg", f2);
            }
        } catch (Exception ex) {
            Logger.getLogger(CardImporterServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}