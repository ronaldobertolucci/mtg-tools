package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrintedFields {

    @Column(name = "printed_name")
    private String printedName;
    @Column(name = "printed_type_line")
    private String printedTypeLine;
    @Column(name = "printed_text", columnDefinition = "TEXT")
    private String printedText;
}
