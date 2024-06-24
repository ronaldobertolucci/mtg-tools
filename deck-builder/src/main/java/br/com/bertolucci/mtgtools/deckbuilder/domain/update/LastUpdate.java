package br.com.bertolucci.mtgtools.deckbuilder.domain.update;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "updates", indexes = {@Index(columnList = "date", name = "updates_date_idx")})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"date"})
@SequenceGenerator(name = "SEQUENCE_UPDATE", sequenceName = "updates_id_seq", allocationSize = 1)
public class LastUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_UPDATE")
    private Integer id;
    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }

}
