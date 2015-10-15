package com.achersoft.mtg.importer.parser;

import com.achersoft.mtg.importer.dao.Set;
import com.achersoft.mtg.importer.dao.Sets;
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

public class SetsDeserializer extends JsonDeserializer<Sets> {
 
    @Override
    public Sets deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<Set> set = new ArrayList();
        Sets sets = new Sets();
        ObjectMapper mapper = new ObjectMapper();
        Iterator<JsonNode> node = ((JsonNode)jp.getCodec().readTree(jp)).elements();
        while(node.hasNext()) {
            set.add(mapper.readValue(node.next().toString(), Set.class));
        }
        sets.setSets(set);
        return sets;
    }
}