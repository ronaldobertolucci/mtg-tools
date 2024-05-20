package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rulings")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"source", "publishedAt", "comment"})
public class Ruling {

    @Transient
    private final RulingValidator validator = new RulingValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    @Column(nullable = false)
    private String source;
    @Column(name = "published_at", nullable = false)
    private LocalDate publishedAt;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    public Ruling(Card card, String source, String publishedAt, String comment) {
        this.card = card;
        this.source = validator.validateSource(source);
        this.comment = validator.validateComment(comment);
        this.publishedAt = validator.validatePublishedAt(publishedAt);
    }

    public Ruling(RulingDto rulingDto) {
        this.source = validator.validateSource(rulingDto.source());
        this.comment = validator.validateComment(rulingDto.comment());
        this.publishedAt = validator.validatePublishedAt(rulingDto.publishedAt());
    }

    public void setSource(String source) {
        this.source = validator.validateSource(source);
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = validator.validatePublishedAt(publishedAt);
    }

    public void setComment(String comment) {
        this.comment = validator.validateComment(comment);
    }

    @Override
    public String toString() {
        return this.comment;
    }
}
