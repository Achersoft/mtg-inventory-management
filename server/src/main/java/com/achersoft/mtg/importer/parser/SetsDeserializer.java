package com.achersoft.mtg.importer.parser;

import com.achersoft.mtg.importer.dao.SetImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetsDeserializer extends JsonDeserializer<SetsImport> {
 
    @Override
    public SetsImport deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<SetImport> set = new ArrayList();
        SetsImport sets = new SetsImport();
    /*    ObjectMapper mapper = new ObjectMapper();
        Iterator<JsonNode> node = ((JsonNode)jp.getCodec().readTree(jp)).elements();
        while(node.hasNext()) {
            set.add(mapper.readValue(node.next().toString(), SetImport.class));
        }
        sets.setSets(set);*/
        return sets;
    }
}