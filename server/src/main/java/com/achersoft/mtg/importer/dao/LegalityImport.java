package com.achersoft.mtg.importer.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalityImport {
    public String format;
    public String legality;
}
