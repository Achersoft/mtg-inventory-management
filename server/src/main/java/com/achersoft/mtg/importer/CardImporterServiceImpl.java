package com.achersoft.mtg.importer;

import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.ForeignImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.achersoft.mtg.importer.persistence.ImporterMapper;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
        sets.getSets().stream().filter((set) -> !set.isOnlineOnly()).forEach((set) -> {
            Logger.getLogger(CardImporterServiceImpl.class.getName()).log(Level.INFO, "Import Set: {0}", set.getName());
            Map vars = new HashMap();
            set.setId(DigestUtils.sha1Hex(set.name));
            mapper.addSet(set);
            set.getCards().stream().filter((card) -> card.layout.equals("normal") || card.layout.equals("split") || 
                    card.layout.equals("flip") || card.layout.equals("double-faced")
            ).forEach((card) -> {
                card.setSetId(set.getId());
                if(card.getOriginalText() == null && card.getText() != null)
                    card.setOriginalText(card.getText());
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
                        cardVariant.setInventoryId(DigestUtils.sha1Hex(cardVariant.getName()+set.getId()+lang.getLanguage()));
                        cardVariant.setLanguage(lang.getLanguage());
                        cardVariant.setMultiverseid(lang.getMultiverseid());
                        mapper.addCard(cardVariant);
                        mapper.addCardToInventory(cardVariant);
                        importImage(cardVariant.getId(), cardVariant.getMultiverseid());
                    } else {
                        card.setId(DigestUtils.sha1Hex(card.getName()+set.getId()+lang.getLanguage()));
                        card.setInventoryId(DigestUtils.sha1Hex(card.getName()+set.getId()+lang.getLanguage()));
                        card.setLanguage(lang.getLanguage());
                        card.setMultiverseid(lang.getMultiverseid());
                        if(card.layout.equalsIgnoreCase("split")) {
                            String id = DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage());
                            CardImport splitCard = CardImport.builder()
                                    .id(id)
                                    .inventoryId(id)
                                    .setId(set.getId())
                                    .name(String.join(" // ", card.names))
                                    .hasChildren(true)
                                    .language(lang.getLanguage())
                                    .layout("split")
                                    .build();
                            card.setInventoryId(id);
                            card.setSplitId(id);
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            mapper.addCard(splitCard);
                            mapper.addCard(card);
                            mapper.addCardToInventory(splitCard);
                            importImage(card.getId(), card.getMultiverseid());
                        } else if(card.layout.equalsIgnoreCase("flip") || card.layout.equalsIgnoreCase("double-faced")) {
                            String id = DigestUtils.sha1Hex(String.join("", card.names)+set.getId()+lang.getLanguage());
                            CardImport flipCard = CardImport.builder()
                                    .id(id)
                                    .inventoryId(id)
                                    .setId(set.getId())
                                    .name(String.join(" | ", card.names))
                                    .hasChildren(true)
                                    .language(lang.getLanguage())
                                    .layout("flip")
                                    .build();
                            card.setInventoryId(id);
                            card.setSplitId(id);
                            card.setSplitSequence(card.names.indexOf(card.name)+1);
                            mapper.addCard(flipCard);
                            mapper.addCard(card);
                            mapper.addCardToInventory(flipCard);
                            importImage(card.getId(), card.getMultiverseid());
                        } else {
                            mapper.addCard(card);
                            mapper.addCardToInventory(card);
                            importImage(card.getId(), card.getMultiverseid());
                        }
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
            URLConnection openConnection = url.openConnection(); 
            openConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            openConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
            openConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            openConnection.setRequestProperty("Cache-Control", "no-cache");
            openConnection.setRequestProperty("Connection", "keep-alive");
            openConnection.setRequestProperty("Cookie", "_ga=GA1.2.1450316419.1468957852; CardDatabaseSettings=1=en-US; BIGipServerWWWNetPool02=3876587786.20480.0000; ASP.NET_SessionId=c54mr0ia350ax4mwmz0oskmz; __utmt=1; __utma=28542179.1450316419.1468957852.1475785426.1475884187.2; __utmb=28542179.2.10.1475884187; __utmc=28542179; __utmz=28542179.1475884187.2.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)");
            openConnection.setRequestProperty("Host", "gatherer.wizards.com");
            openConnection.setRequestProperty("Pragma", "no-cache");
            openConnection.setRequestProperty("Referer", "http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid="+multiverseId);
            openConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");

            try (InputStream in = new BufferedInputStream(openConnection.getInputStream())) {
                if(in.available()>0) {
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(f2));
                    for ( int i; (i = in.read()) != -1; ) {
                        out.write(i);
                    }  
                    out.close();
                } else {
                    BufferedImage image = ImageIO.read(new File(new File(System.getProperty("catalina.base"), "webapps/titan/images"), "blank.jpeg"));
                    ImageIO.write(image, "jpeg", f2);
                }
            }
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            Logger.getLogger(CardImporterServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}