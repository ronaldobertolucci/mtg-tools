package br.com.bertolucci.mtgtools.shared.card;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public record CardDto(

        @SerializedName("collector_number")
        String collectorNumber,
        @SerializedName("image_status")
        String imageStatus,
        String rarity,
        @SerializedName("digital")
        Boolean isDigital,
        @SerializedName("card_faces")
        List<FaceDto> faces,
        Map<String, String> legalities,
        String name,
        String toughness,
        String power,
        @SerializedName("type_line")
        String typeLine,
        String loyalty,
        @SerializedName("oracle_text")
        String oracleText,
        @SerializedName("mana_cost")
        String manaCost,
        @SerializedName("image_uris")
        Map<String, String> imageUris,
        @SerializedName("set")
        String setCode,
        Double cmc
) {
}
