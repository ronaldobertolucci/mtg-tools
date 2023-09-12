package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.FindSetService;
import br.com.bertolucci.mtgtools.deckbuilder.application.util.CreateManaCostFromFacesService;
import br.com.bertolucci.mtgtools.deckbuilder.application.util.CreateOracleTextFromFacesService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;

import java.util.ArrayList;
import java.util.List;

public class CardFromDtoService {

    private CollectionService collectionService;
    private FindSetService findSetService;
    private CardDto cardDto;
    private Set set;

    public CardFromDtoService(CollectionService collectionService, FindSetService findSetService,
                              CardDto cardDto, Set set) {
        this.collectionService = collectionService;
        this.findSetService = findSetService;
        this.cardDto = cardDto;
        this.set = set;
    }

    public Card get() {
        Card card = new Card(cardDto);
        createParts(cardDto, card);
        createLegalities(cardDto, card);
        createFaces(cardDto, card);

        if (set == null) {
            card.setSet(createSet(cardDto));
            return card;
        }

        card.setSet(set);
        return card;
    }

    private Set createSet(CardDto cardDto) {
        if (cardDto.setCode() == null || cardDto.setCode().isEmpty()) {
            return null;
        }

        return findSetService.find(cardDto.setCode());
    }

    private void createParts(CardDto cardDto, Card card) {
        if (cardDto.partDtos() == null) {
            return;
        }

        List<Part> parts = new ArrayList<>();
        cardDto.partDtos().forEach(
                partDto -> parts.add(new Part(card, partDto.name(), partDto.typeLine(), partDto.partType())));

        parts.forEach(card::addPart);
    }

    private void createLegalities(CardDto cardDto, Card card) {
        if (cardDto.legalities() == null) {
            return;
        }

        Legalities legalities = new Legalities(

                cardDto.legalities().get("standard") != null
                        ? Legality.valueOf(cardDto.legalities().get("standard").toUpperCase())
                        : null,

                cardDto.legalities().get("alchemy") != null
                        ? Legality.valueOf(cardDto.legalities().get("alchemy").toUpperCase())
                        : null,

                cardDto.legalities().get("brawl") != null
                        ? Legality.valueOf(cardDto.legalities().get("brawl").toUpperCase())
                        : null,

                cardDto.legalities().get("commander") != null
                        ? Legality.valueOf(cardDto.legalities().get("commander").toUpperCase())
                        : null,

                cardDto.legalities().get("explorer") != null
                        ? Legality.valueOf(cardDto.legalities().get("explorer").toUpperCase())
                        : null,

                cardDto.legalities().get("historic") != null
                        ? Legality.valueOf(cardDto.legalities().get("historic").toUpperCase())
                        : null,

                cardDto.legalities().get("legacy") != null
                        ? Legality.valueOf(cardDto.legalities().get("legacy").toUpperCase())
                        : null,

                cardDto.legalities().get("modern") != null
                        ? Legality.valueOf(cardDto.legalities().get("modern").toUpperCase())
                        : null,

                cardDto.legalities().get("pioneer") != null
                        ? Legality.valueOf(cardDto.legalities().get("pioneer").toUpperCase())
                        : null,

                cardDto.legalities().get("pauper") != null
                        ? Legality.valueOf(cardDto.legalities().get("pauper").toUpperCase())
                        : null,


                cardDto.legalities().get("oathbreaker") != null
                        ? Legality.valueOf(cardDto.legalities().get("oathbreaker").toUpperCase())
                        : null,

                cardDto.legalities().get("vintage") != null
                        ? Legality.valueOf(cardDto.legalities().get("vintage").toUpperCase())
                        : null

        );

        card.setLegalities(legalities);
    }

    private void createFaces(CardDto cardDto, Card card) {
        if (cardDto.faceDtos() == null) {
            return;
        }

        List<Face> faces = new ArrayList<>();
        cardDto.faceDtos().forEach(faceDto -> {

            Face face = new Face(faceDto);
            face.setCard(card);
            faces.add(face);

        });

        faces.forEach(card::addFace);
        CreateManaCostFromFacesService.create(card);
        CreateOracleTextFromFacesService.create(card);
    }
}
