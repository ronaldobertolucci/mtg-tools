package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import jakarta.persistence.*;

@Embeddable
public class PrintedFields {

    @Column(name = "printed_name")
    private String printedName;
    @Column(name = "printed_type_line")
    private String printedTypeLine;
    @Column(name = "printed_text", columnDefinition = "TEXT")
    private String printedText;

    public PrintedFields() {
    }

    public PrintedFields(String printedName, String printedTypeLine, String printedText) {
        this.printedName = printedName;
        this.printedTypeLine = printedTypeLine;
        this.printedText = printedText;
    }

    public String getPrintedName() {
        return printedName;
    }

    public void setPrintedName(String printedName) {
        this.printedName = printedName;
    }

    public String getPrintedTypeLine() {
        return printedTypeLine;
    }

    public void setPrintedTypeLine(String printedTypeLine) {
        this.printedTypeLine = printedTypeLine;
    }

    public String getPrintedText() {
        return printedText;
    }

    public void setPrintedText(String printedText) {
        this.printedText = printedText;
    }
}
