package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sets")
public class Set {

    @Transient
    private final SetValidator validator = new SetValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String code;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private SetType type;
    @Column(name = "released_at")
    private LocalDate releasedAt;
    @Column(name = "total_cards", nullable = false)
    private Integer totalCards;
    @Column(name = "is_digital", nullable = false)
    private Boolean isDigital;
    @OneToMany(mappedBy = "set", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    @Column(name = "image_uri", nullable = false)
    private String imageUri;

    public Set() {
    }

    public Set(String name, String code, String type, Integer totalCards, Boolean isDigital, String imageUri, String releasedAt) {
        this.name = validator.validateName(name);
        this.code = validator.validateCode(code);
        this.type = validator.validateType(type);
        this.totalCards = validator.validateTotalCards(totalCards);
        this.isDigital = isDigital;
        this.imageUri = validator.validateImageUri(imageUri);
        this.releasedAt = validator.validateReleasedAt(releasedAt);
    }

    public Set(SetDto setDto) {
        this.name = validator.validateName(setDto.name());
        this.code = validator.validateCode(setDto.code());
        this.type = validator.validateType(setDto.type());
        this.totalCards = validator.validateTotalCards(setDto.totalCards());
        this.isDigital = setDto.isDigital();
        this.imageUri = setDto.imageUri();
        this.releasedAt = validator.validateReleasedAt(setDto.releasedAt());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = validator.validateCode(code);
    }

    @Enumerated(EnumType.STRING)
    public SetType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = validator.validateType(type);
    }

    public LocalDate getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(String releasedAt) {
        this.releasedAt = validator.validateReleasedAt(releasedAt);
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = validator.validateTotalCards(totalCards);
    }

    public Boolean isDigital() {
        return isDigital;
    }

    public void setDigital(Boolean digital) {
        isDigital = digital;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = validator.validateImageUri(imageUri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set set = (Set) o;
        return this.name.equalsIgnoreCase(set.getName()) && this.code.equalsIgnoreCase(set.getCode());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
