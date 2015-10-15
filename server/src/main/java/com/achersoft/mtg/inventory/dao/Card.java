/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.achersoft.mtg.inventory.dao;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Shaun
 */
@Builder
@Data
public class Card {
    public String id;
    public String name;
    public String setId;
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
 /*   
    "layout":"normal",
	"type":"Creature — Elemental",
	"types":["Creature"],
	"colors":["Blue"],
	"multiverseid":94,
	"name":"Air Elemental",
	"subtypes":["Elemental"],
	"originalType":"Summon — Elemental",
	"cmc":5,
	"rarity":"Uncommon",
	"artist":"Richard Thomas",
	"power":"4",
	"toughness":"4",
	"manaCost":"{3}{U}{U}",
	"text":"Flying",
	"originalText":"Flying",
	"flavor":"These spirits of the air are winsome and wild, and cannot be truly contained. Only marginally intelligent, they often substitute whimsy for strategy, delighting in mischief and mayhem.",
	"imageName":"air elemental",
	"legalities":[{"format":"Commander","legality":"Legal"},
	              {"format":"Freeform","legality":"Legal"},
				  {"format":"Legacy","legality":"Legal"},
				  {"format":"Modern","legality":"Legal"},
				  {"format":"Prismatic","legality":"Legal"},
				  {"format":"Singleton 100","legality":"Legal"},
				  {"format":"Tribal Wars Legacy","legality":"Legal"},
				  {"format":"Vintage","legality":"Legal"}],
	"printings":["LEA","LEB","2ED","CED","CEI","3ED","4ED","5ED","PO2","6ED","S99","BRB","BTD","7ED","8ED","9ED","10E","DD2","M10","DPA","ME4","DD3_JVC"],
	"id":"926234c2fe8863f49220a878346c4c5ca79b6046"},
	*/
}
