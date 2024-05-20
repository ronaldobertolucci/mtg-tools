package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sets")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "code"})
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SetType type;
    @Column(name = "released_at")
    private LocalDate releasedAt;
    @Column(name = "total_cards", nullable = false)
    private Integer totalCards;
    @OneToMany(mappedBy = "set", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    @Column(name = "image_uri", nullable = false)
    private String imageUri;

    public Set(String name, String code, String type, Integer totalCards, String imageUri, String releasedAt) {
        this.name = validator.validateName(name);
        this.code = validator.validateCode(code);
        this.type = validator.validateType(type);
        this.imageUri = validator.validateImageUri(imageUri);
        this.releasedAt = LocalDate.parse(releasedAt);
        this.totalCards = totalCards;
    }

    public Set(SetDto setDto) {
        this.name = validator.validateName(setDto.name());
        this.code = validator.validateCode(setDto.code());
        this.type = validator.validateType(setDto.type());
        this.imageUri = setDto.imageUri();
        this.releasedAt = LocalDate.parse(setDto.releasedAt());
        this.totalCards = setDto.totalCards();
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public void setCode(String code) {
        this.code = validator.validateCode(code);
    }

    public void setType(String type) {
        this.type = validator.validateType(type);
    }

    public void setImageUri(String imageUri) {
        this.imageUri = validator.validateImageUri(imageUri);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
