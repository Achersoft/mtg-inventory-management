package com.achersoft.mtg.importer.dao;

import com.achersoft.mtg.importer.parser.SetsDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Shaun
 */
@Builder
@Data
@NoArgsConstructor 
@AllArgsConstructor
@JsonDeserialize(using = SetsDeserializer.class)
public class Sets {
    public List<Set> sets;
}