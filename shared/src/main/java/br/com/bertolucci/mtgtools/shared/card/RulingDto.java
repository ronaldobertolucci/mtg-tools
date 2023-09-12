package br.com.bertolucci.mtgtools.shared.card;

import com.google.gson.annotations.SerializedName;

public record RulingDto(

        String source,
        @SerializedName("published_at")
        String publishedAt,
        String comment

) {
}
