package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;

public class CreateOracleTextFromFacesService {

    public static void create(Card card) {

        if (card.getOracleText() != null && !card.getOracleText().isEmpty()) {
            card.setOracleText("");
        }

        card.getFaces().forEach(face -> {

            if (face.getOracleText() != null && !face.getOracleText().isEmpty()) {

                if (card.getOracleText() != null && !card.getOracleText().isEmpty()) {
                    card.setOracleText(card.getOracleText().trim() + "\n\n//\n\n" + face.getOracleText().trim());
                    return;
                }

                card.setOracleText(face.getOracleText().trim());

            }
        });
    }

}
