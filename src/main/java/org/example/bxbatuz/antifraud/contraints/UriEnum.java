package org.example.bxbatuz.antifraud.contraints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UriEnum {
    BASE_URI("https://seurityidentifier.linguaway.uz/form/");
    private final String val;
}
