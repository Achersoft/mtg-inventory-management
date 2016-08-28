package com.achersoft.mtg.price.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PriceScale {
    public Double nm;
    public Double sp;
    public Double mp;
    public Double hp;
    public Double fnm;
    public Double fsp;
    public Double fmp;
    public Double fhp;
}
