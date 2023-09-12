package br.com.bertolucci.mtgtools.shared.card;

import com.google.gson.annotations.SerializedName;

public record PartDto(

        String name,
        @SerializedName("type_line")
        String typeLine,
        @SerializedName("component")
        String partType

) {
}
