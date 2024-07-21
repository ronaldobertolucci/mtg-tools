package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import java.util.ArrayList;
import java.util.List;

public class GetSymbolList {

    public static List<String> get(String manaCost) {
        List<String> symbols = new ArrayList<>();

        String[] splited = manaCost.trim().split("}");
        for (String s : splited) {
            symbols.add("{" + s.replaceAll("[^½∞A-Za-z0-9]", "") + "}");
        }

        return symbols;
    }
}
