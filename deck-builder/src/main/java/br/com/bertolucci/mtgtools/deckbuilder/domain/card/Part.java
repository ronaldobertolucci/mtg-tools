package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.PartDto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "parts")
public class Part {

    @Transient
    private final PartValidator validator = new PartValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(name = "type_line", nullable = false)
    private String typeLine;
    @Enumerated(EnumType.ORDINAL)
    private PartType type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    public Part() {
    }

    public Part(Card card, String name, String typeLine, String type) {
        this.card = card;
        this.name = validator.validateName(name);
        this.typeLine = validator.validateTypeLine(typeLine);
        this.type = validator.validateType(type);
    }

    public Part(PartDto partDto) {
        this.name = validator.validateName(partDto.name());
        this.typeLine = validator.validateTypeLine(partDto.typeLine());
        this.type = validator.validateType(partDto.partType());
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

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = validator.validateTypeLine(typeLine);
    }

    @Enumerated(EnumType.STRING)
    public PartType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = validator.validateType(type);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return this.name.equalsIgnoreCase(part.getName()) && this.typeLine.equalsIgnoreCase(part.getTypeLine())
                && Objects.equals(card, part.card);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + typeLine.hashCode();
        result = 31 * result + card.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
