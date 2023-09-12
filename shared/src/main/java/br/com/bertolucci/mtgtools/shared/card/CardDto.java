package br.com.bertolucci.mtgtools.shared.card;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public record CardDto(
        @SerializedName("border_color")
        String borderColor,
        @SerializedName("collector_number")
        String collectorNumber,
        @SerializedName("image_status")
        String imageStatus,
        String rarity,
        @SerializedName("digital")
        Boolean isDigital,
        String[] keywords,
        String lang,
        @SerializedName("oracle_id")
        String oracleId,
        @SerializedName("rulings_uri")
        String rulingsUri,
        @SerializedName("card_faces")
        List<FaceDto> faceDtos,
        Map<String, String> legalities,
        String artist,
        @SerializedName("flavor_text")
        String flavorText,
        @SerializedName("name")
        String name,
        String toughness,
        String power,
        @SerializedName("type_line")
        String typeLine,
        @SerializedName("layout")
        String layoutType,
        String loyalty,
        @SerializedName("oracle_text")
        String oracleText,
        @SerializedName("mana_cost")
        String manaCost,
        @SerializedName("image_uris")
        Map<String, String> imageUris,
        @SerializedName("all_parts")
        List<PartDto> partDtos,
        @SerializedName("set")
        String setCode,
        Double cmc,
        @SerializedName("printed_name")
        String printedName,
        @SerializedName("printed_type_line")
        String printedTypeLine,
        @SerializedName("printed_text")
        String printedText
) {
}
