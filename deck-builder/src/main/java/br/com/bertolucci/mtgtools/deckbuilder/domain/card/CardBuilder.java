package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.util.CreateManaCostFromFacesService;
import br.com.bertolucci.mtgtools.deckbuilder.application.util.CreateOracleTextFromFacesService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.apache.commons.lang3.Validate;

public class CardBuilder {

    private Card card = new Card();

    public CardBuilder(CardDto cardDto, Set set) {
        this.setSet(set);
        this.setTypeLine(cardDto.typeLine());
        this.setImageStatus(CardImageStatus.valueOf(cardDto.imageStatus().toUpperCase()));
        this.setCollectorNumber(cardDto.collectorNumber());
        this.setName(cardDto.name());
        this.setIsDigital(cardDto.isDigital());
        this.setRarity(CardRarity.valueOf(cardDto.rarity().toUpperCase()));
        this.setToughness(cardDto.toughness());
        this.setPower(cardDto.power());
        this.setLoyalty(cardDto.loyalty());
        this.setOracleText(cardDto.oracleText());
        this.setManaCost(cardDto.manaCost());
        this.setCmc(cardDto.cmc());
        this.setImageUri(cardDto.imageUris() != null ? cardDto.imageUris().get("png"): null);

        if (cardDto.legalities() != null) {
            addCardLegality(cardDto, "standard");
            addCardLegality(cardDto, "commander");
            addCardLegality(cardDto, "historic");
            addCardLegality(cardDto, "legacy");
            addCardLegality(cardDto, "modern");
            addCardLegality(cardDto, "pioneer");
        }

        if (cardDto.faces() != null && !cardDto.faces().isEmpty()) {
            cardDto.faces().forEach(face -> this.card.addFace(new FaceBuilder(face).build()));
            CreateManaCostFromFacesService.create(card);
            CreateOracleTextFromFacesService.create(card);
        }
    }

    public CardBuilder setSet(Set set) {
        this.card.setSet(Validate.notNull(set, "O set do card não pode ser nulo"));
        return this;
    }

    public CardBuilder setTypeLine(String typeLine) {
        this.card.setTypeLine(typeLine);
        return this;
    }

    public CardBuilder setName(String name) {
        this.card.setName(Validate.notBlank(name, "O nome do card não pode estar em branco"));
        return this;
    }

    public CardBuilder setCollectorNumber(String collectorNumber) {
        this.card.setCollectorNumber(Validate.notBlank(collectorNumber,
                "O  número de colecionador do card não pode estar em branco"));
        return this;
    }

    public CardBuilder setImageStatus(CardImageStatus cardImageStatus) {
        this.card.setImageStatus(cardImageStatus);
        return this;
    }

    public CardBuilder setToughness(String toughness) {
        this.card.setToughness(toughness);
        return this;
    }

    public CardBuilder setPower(String power) {
        this.card.setPower(power);
        return this;
    }

    public CardBuilder setLoyalty(String loyalty) {
        this.card.setLoyalty(loyalty);
        return this;
    }

    public CardBuilder setIsDigital(Boolean isDigital) {
        this.card.setIsDigital(isDigital);
        return this;
    }

    public CardBuilder setOracleText(String oracleText) {
        this.card.setOracleText(oracleText);
        return this;
    }

    public CardBuilder setManaCost(String manaCost) {
        this.card.setManaCost(manaCost);
        return this;
    }

    public CardBuilder setCmc(Double cmc) {
        Validate.isTrue(cmc >= 0.0, "O cmc não pode ser menor do que zero");
        this.card.setCmc(cmc);
        return this;
    }

    public CardBuilder setRarity(CardRarity cardRarity) {
        this.card.setCardRarity(cardRarity);
        return this;
    }

    public CardBuilder setImageUri(String imageUri) {
        this.card.setImageUri(imageUri);
        return this;
    }

    private void addCardLegality(CardDto cardDto, String format) {
        if (cardDto.legalities().get(format) != null) {
            this.card.addCardLegality(new CardLegality(
                    this.card,
                    Legality.valueOf(cardDto.legalities().get(format).toUpperCase()),
                    DeckFormat.valueOf(format.toUpperCase())
            ));
        }
    }

    public Card build() {
        return this.card;
    }

}
