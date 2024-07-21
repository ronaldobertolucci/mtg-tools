package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractSymbolsService {

    public static Map<Integer, List<String>> extract(String manaCost) {
        Map<Integer, List<String>> symbolsMap = new HashMap<>();

        if (manaCost == null || manaCost.isEmpty()) {
            return symbolsMap;
        }

        if (!manaCost.contains("//")) {
            symbolsMap.put(0, getSymbolList(manaCost));
            return symbolsMap;
        }

        String[] doubleCard = manaCost.split("//");
        symbolsMap.put(0,getSymbolList(doubleCard[0]));
        symbolsMap.put(1,getSymbolList(doubleCard[1]));
        return symbolsMap;
    }

    private static List<String> getSymbolList(String manaCost) {
        return GetSymbolList.get(manaCost);
    }

}
