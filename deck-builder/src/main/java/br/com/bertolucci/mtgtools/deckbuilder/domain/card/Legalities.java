package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Legalities {

    @Enumerated(EnumType.ORDINAL)
    private Legality standard;
    @Enumerated(EnumType.ORDINAL)
    private Legality alchemy;
    @Enumerated(EnumType.ORDINAL)
    private Legality brawl;
    @Enumerated(EnumType.ORDINAL)
    private Legality commander;
    @Enumerated(EnumType.ORDINAL)
    private Legality explorer;
    @Enumerated(EnumType.ORDINAL)
    private Legality historic;
    @Enumerated(EnumType.ORDINAL)
    private Legality legacy;
    @Enumerated(EnumType.ORDINAL)
    private Legality modern;
    @Enumerated(EnumType.ORDINAL)
    private Legality pioneer;
    @Enumerated(EnumType.ORDINAL)
    private Legality pauper;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "oath_breaker")
    private Legality oathBreaker;
    @Enumerated(EnumType.ORDINAL)
    private Legality vintage;

    public Legalities() {
    }

    public Legalities(Legality standard, Legality alchemy, Legality brawl, Legality commander, Legality explorer,
                      Legality historic, Legality legacy, Legality modern, Legality pioneer, Legality pauper,
                      Legality oathBreaker, Legality vintage) {
        this.standard = standard;
        this.alchemy = alchemy;
        this.brawl = brawl;
        this.commander = commander;
        this.explorer = explorer;
        this.historic = historic;
        this.legacy = legacy;
        this.modern = modern;
        this.pioneer = pioneer;
        this.pauper = pauper;
        this.oathBreaker = oathBreaker;
        this.vintage = vintage;
    }

    @Enumerated(EnumType.STRING)
    public Legality getStandard() {
        return standard;
    }

    public void setStandard(Legality standard) {
        this.standard = standard;
    }

    @Enumerated(EnumType.STRING)
    public Legality getAlchemy() {
        return alchemy;
    }

    public void setAlchemy(Legality alchemy) {
        this.alchemy = alchemy;
    }

    @Enumerated(EnumType.STRING)
    public Legality getBrawl() {
        return brawl;
    }

    public void setBrawl(Legality brawl) {
        this.brawl = brawl;
    }

    @Enumerated(EnumType.STRING)
    public Legality getCommander() {
        return commander;
    }

    public void setCommander(Legality commander) {
        this.commander = commander;
    }

    @Enumerated(EnumType.STRING)
    public Legality getExplorer() {
        return explorer;
    }

    public void setExplorer(Legality explorer) {
        this.explorer = explorer;
    }

    @Enumerated(EnumType.STRING)
    public Legality getHistoric() {
        return historic;
    }

    public void setHistoric(Legality historic) {
        this.historic = historic;
    }

    @Enumerated(EnumType.STRING)
    public Legality getLegacy() {
        return legacy;
    }

    public void setLegacy(Legality legacy) {
        this.legacy = legacy;
    }

    @Enumerated(EnumType.STRING)
    public Legality getModern() {
        return modern;
    }

    public void setModern(Legality modern) {
        this.modern = modern;
    }

    @Enumerated(EnumType.STRING)
    public Legality getPioneer() {
        return pioneer;
    }

    public void setPioneer(Legality pioneer) {
        this.pioneer = pioneer;
    }

    @Enumerated(EnumType.STRING)
    public Legality getPauper() {
        return pauper;
    }

    public void setPauper(Legality pauper) {
        this.pauper = pauper;
    }

    @Enumerated(EnumType.STRING)
    public Legality getOathBreaker() {
        return oathBreaker;
    }

    public void setOathBreaker(Legality oathBreaker) {
        this.oathBreaker = oathBreaker;
    }

    @Enumerated(EnumType.STRING)
    public Legality getVintage() {
        return vintage;
    }

    public void setVintage(Legality vintage) {
        this.vintage = vintage;
    }
}
