package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.shared.set.SetDto;
import org.apache.commons.lang3.Validate;

import java.time.LocalDate;

public class SetBuilder {

    private Set set = new Set();

    public SetBuilder(SetDto setDto) {
        this.setCode(setDto.code());
        this.setName(setDto.name());
        this.setType(SetType.valueOf(setDto.type().toUpperCase()));
        this.setTotalCards(setDto.totalCards());
        this.setImageUri(setDto.imageUri());
        this.setReleasedAt(LocalDate.parse(setDto.releasedAt()));
        this.setParentSet(setDto.parentSet());
    }

    private SetBuilder setParentSet(String parentSet) {
        this.set.setParentSet(parentSet);
        return this;
    }

    public SetBuilder setCode(String code) {
        this.set.setCode(Validate.notBlank(code, "O código do set não pode estar em branco"));
        return this;
    }

    public SetBuilder setName(String name) {
        this.set.setName(Validate.notBlank(name, "O nome do set não pode estar em branco"));
        return this;
    }

    public SetBuilder setType(SetType type) {
        this.set.setType(type);
        return this;
    }

    public SetBuilder setTotalCards(Integer totalCards) {
        this.set.setTotalCards(totalCards);
        return this;
    }

    public SetBuilder setImageUri(String imageUri) {
        this.set.setImageUri(Validate.notBlank(imageUri, "O link da imagem do set não pode estar em branco"));
        return this;
    }

    public SetBuilder setReleasedAt(LocalDate localDate) {
        this.set.setReleasedAt(localDate);
        return this;
    }

    public Set build() {
        return this.set;
    }

}
