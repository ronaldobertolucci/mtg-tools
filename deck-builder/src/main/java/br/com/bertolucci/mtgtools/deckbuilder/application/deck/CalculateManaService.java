package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.shared.card.Color;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CalculateManaService {

    private final Double CARDCOUNTREF = 36.0;
    private final Double MANACOUNTREF = 24.0;
    private final Double MANACOSTPERCARDREF = 3.0;
    private Double globalModifier;
    private Deck deck;
    private Double totalNonLandCardCount;
    private Double totalCmc;
    private Map<Color, Integer> totalColouredManaCost;
    @Getter
    private Map<Color, Double> results = new HashMap<>();
    @Getter
    private Double avgCmc;
    @Getter
    private Double suggestedManaCards;

    public CalculateManaService(Double globalModifier, Deck deck) {
        this.globalModifier = globalModifier;
        this.deck = deck;

        this.totalNonLandCardCount = getTotalNonLandsCards();
        this.totalCmc = getTotalCmc();
        this.totalColouredManaCost = getTotalColouredManaCost();
        this.calculate();
    }

    private void calculate() {
        this.avgCmc = BigDecimal.valueOf((totalCmc > 0.0 && totalNonLandCardCount > 0.0)
                    ? totalCmc / totalNonLandCardCount
                    : 0.0)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();

        Double cardCountModifier = totalNonLandCardCount / CARDCOUNTREF;
        Double manaCostModifier = avgCmc / MANACOSTPERCARDREF;

        this.suggestedManaCards = BigDecimal.valueOf(MANACOUNTREF * cardCountModifier * manaCostModifier * globalModifier)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        getResult();
    }

    private void getResult() {
        Integer totalColoredMana = 0;
        for (Color c : totalColouredManaCost.keySet()) {
            totalColoredMana += totalColouredManaCost.get(c);
        }

        for (Color c : totalColouredManaCost.keySet()) {
            Integer cost = totalColouredManaCost.get(c);

            Double manaColorTotal = 0.0;
            try {
                manaColorTotal = BigDecimal.valueOf((cost.doubleValue() / totalColoredMana.doubleValue()) * suggestedManaCards)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();;
            } catch (Exception ignored) {}

            results.put(c, manaColorTotal);
        }
    }

    private Double getTotalNonLandsCards() {
        return GetTotalNonLandCardsService.getTotal(deck).doubleValue();
    }

    private Double getTotalCmc() {
        return GetTotalDeckCmcService.getTotal(deck);
    }

    private Map<Color, Integer> getTotalColouredManaCost() {
        return GetTotalCostByColorService.getTotal(deck);
    }
}
