package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;

public class CreateManaCostFromFacesService {

    public static void create(Card card) {

        if (card.getManaCost() != null && !card.getManaCost().isEmpty()) {
            return;
        }

        card.getFaces().forEach(face -> {

            if (face.getManaCost() != null && !face.getManaCost().isEmpty()) {

                if (card.getManaCost() != null && !card.getManaCost().isEmpty()) {
                    card.setManaCost(card.getManaCost().trim() + " // " + face.getManaCost().trim());
                    return;
                }

                card.setManaCost(face.getManaCost().trim());

            }
        });
    }
}
