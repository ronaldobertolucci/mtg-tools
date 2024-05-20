package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.PartDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "parts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"card", "name", "typeLine"})
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
    @Enumerated(EnumType.STRING)
    private PartType type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

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

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = validator.validateTypeLine(typeLine);
    }

    public void setType(String type) {
        this.type = validator.validateType(type);
    }

    @Override
    public String toString() {
        return name;
    }
}
