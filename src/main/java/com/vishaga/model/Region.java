package com.vishaga.model;

import java.util.stream.Stream;

public enum Region {

    AFRICA("Africa"),
    ASIA("Asia"),
    EUROPE("Europe"),
    AMERICAS("Americas"),
    OCEANIA("Oceania"),
    NA("");

    final String desc;

    Region(String desc){
        this.desc = desc;
    }
    public static Region from(String name){
        return Stream.of(Region.values()).filter(each -> each.desc.equals(name.trim())).findFirst().orElse(NA);
    }
}
