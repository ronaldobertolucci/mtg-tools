package br.com.bertolucci.mtgtools.shared.card;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record FaceDto(

        @SerializedName("flavor_text")
        String flavorText,
        @SerializedName("name")
        String name,
        String toughness,
        String power,
        @SerializedName("type_line")
        String typeLine,
        String loyalty,
        @SerializedName("mana_cost")
        String manaCost,
        @SerializedName("oracle_text")
        String oracleText,
        @SerializedName("oracle_id")
        String oracleId,
        @SerializedName("image_uris")
        Map<String, String> imageUris,
        @SerializedName("printed_name")
        String printedName,
        @SerializedName("printed_type_line")
        String printedTypeLine,
        @SerializedName("printed_text")
        String printedText

) {
}
