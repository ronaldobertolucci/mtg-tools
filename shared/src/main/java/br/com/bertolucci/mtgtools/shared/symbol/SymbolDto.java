package br.com.bertolucci.mtgtools.shared.symbol;

import com.google.gson.annotations.SerializedName;

public record SymbolDto(

        String symbol,
        @SerializedName("english")
        String description,
        @SerializedName("represents_mana")
        Boolean representsMana,
        @SerializedName("mana_value")
        Double manaValue,
        @SerializedName("svg_uri")
        String imageUri

) {
}
