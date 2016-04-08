package com.achersoft.mtg.card.dao;

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
public class CardInventory {
    private int NM;
    private double nmp;
    private int SP;
    private double spp;
    private int MP;
    private double mpp;
    private int HP;
    private double hpp;
    private int FNM;
    private double fnmp;
    private int FSP;
    private double fspp;
    private int FMP;
    private double fmpp;
    private int FHP;
    private double fhpp;
}
