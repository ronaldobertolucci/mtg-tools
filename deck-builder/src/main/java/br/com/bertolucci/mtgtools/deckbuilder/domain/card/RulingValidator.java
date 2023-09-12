package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;

import java.time.LocalDate;

public class RulingValidator {

    public String validateComment(String comment) {
        if (NotNullOrEmptyValidator.isValid(comment)) {
            return comment.trim();
        }

        throw new IllegalArgumentException("O comentário da regra não pode ser nulo ou vazio");
    }

    public String validateSource(String source) {
        if (NotNullOrEmptyValidator.isValid(source)) {
            return source.trim();
        }

        throw new IllegalArgumentException("A fonte da regra não pode ser nula ou vazia");
    }

    public LocalDate validatePublishedAt(String publishedAt) {
        if (NotNullOrEmptyValidator.isValid(publishedAt)) {
            try {
                return LocalDate.parse(publishedAt);
            } catch (Exception ignored) {
                throw new IllegalArgumentException("O formato de data para a regra não é valido");
            }
        }

        throw new IllegalArgumentException("A data para a regra não pode ser nula ou vazia");
    }

}
