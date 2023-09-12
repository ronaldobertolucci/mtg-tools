package br.com.bertolucci.mtgtools.shared.set;

import com.google.gson.annotations.SerializedName;

public record SetDto(

        String code,
        String name,
        @SerializedName("set_type")
        String type,
        @SerializedName("card_count")
        Integer totalCards,
        @SerializedName("digital")
        Boolean isDigital,
        @SerializedName("icon_svg_uri")
        String imageUri,
        @SerializedName("released_at")
        String releasedAt,
        @SerializedName("search_uri")
        String cardsUri,
        @SerializedName("parent_set_code")
        String parentSet

) {
}
