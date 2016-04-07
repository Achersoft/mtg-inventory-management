/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.achersoft.mtg.card.dao;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Shaun
 */
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    public String id;
    public String name;
    public String setId;
    public String set;
    public String language;
    public String manaCost;
    public String type;
    public String subType;
    public Boolean isCreature;
    public String rarity;
    public String text;
    public String oracle;
    public String artist;
    public String power;
    public String toughness;
    public String number;
    public String multiverseId;
    public Date releaseDate;
    public String splitId;
    public Boolean isFront;
    public int NM;
    public double nmp;
    public int SP;
    public double spp;
    public int MP;
    public double mpp;
    public int HP;
    public double hpp;
    public int FNM;
    public double fnmp;
    public int FSP;
    public double fspp;
    public int FMP;
    public double fmpp;
    public int FHP;
    public double fhpp;
}
