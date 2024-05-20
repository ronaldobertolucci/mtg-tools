package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Legalities {

    @Enumerated(EnumType.STRING)
    private Legality standard;
    @Enumerated(EnumType.STRING)
    private Legality alchemy;
    @Enumerated(EnumType.STRING)
    private Legality brawl;
    @Enumerated(EnumType.STRING)
    private Legality commander;
    @Enumerated(EnumType.STRING)
    private Legality explorer;
    @Enumerated(EnumType.STRING)
    private Legality historic;
    @Enumerated(EnumType.STRING)
    private Legality legacy;
    @Enumerated(EnumType.STRING)
    private Legality modern;
    @Enumerated(EnumType.STRING)
    private Legality pioneer;
    @Enumerated(EnumType.STRING)
    private Legality pauper;
    @Enumerated(EnumType.STRING)
    @Column(name = "oath_breaker")
    private Legality oathBreaker;
    @Enumerated(EnumType.STRING)
    private Legality vintage;
}
