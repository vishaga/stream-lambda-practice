package com.vishaga.model;

import java.util.stream.Stream;

public enum IntermediateRegion {
    CARIBBEAN("Caribbean"),
    CENTRAL_AMERICA("Central America"),
    SOUTH_AMERICA("South America"),
    MIDDLE_AFRICA("Middle Africa"),
    WEST_AFRICA("Western Africa"),
    SOUTHERN_AFRICA("Southern Africa"),
    EASTERN_AFRICA("Eastern Africa"),
    CHANNEL_ISLANDS("Channel Islands"),
    NA("");

    final String desc;

    IntermediateRegion(String desc){
        this.desc = desc;
    }

    public static IntermediateRegion from(String name){
        return Stream.of(IntermediateRegion.values()).filter(each -> each.desc.equals(name.trim())).findFirst().orElse(NA);
    }
}
