package org.example.bxbatuz.antifraud.contraints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UriEnum {
    BASE_URI("https://seurityidentifier.linguaway.uz/form/");
//    BASE_URI("http://localhost:9091/form/");
    private final String val;
}
