package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rulings")
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

    public Ruling() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = validator.validateSource(source);
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = validator.validatePublishedAt(publishedAt);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = validator.validateComment(comment);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ruling ruling = (Ruling) object;
        return this.source.equalsIgnoreCase(ruling.getSource())
                && this.publishedAt.equals(ruling.getPublishedAt())
                && this.comment.equalsIgnoreCase(ruling.getComment());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + comment.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + publishedAt.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.comment;
    }
}
