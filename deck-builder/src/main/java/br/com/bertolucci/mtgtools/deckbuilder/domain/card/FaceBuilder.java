package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import org.apache.commons.lang3.Validate;

public class FaceBuilder {

    private Face face = new Face();

    public FaceBuilder(FaceDto faceDto) {
        this.setName(faceDto.name());
        this.setToughness(faceDto.toughness());
        this.setPower(faceDto.power());
        this.setTypeLine(faceDto.typeLine());
        this.setLoyalty(faceDto.loyalty());
        this.setManaCost(faceDto.manaCost());
        this.setOracleText(faceDto.oracleText());
        this.setImageUri(faceDto.imageUris() != null ? faceDto.imageUris().get("png"): null);
    }

    public FaceBuilder setName(String name) {
        this.face.setName(Validate.notBlank(name, "O nome da face não pode estar em branco"));
        return this;
    }

    public FaceBuilder setTypeLine(String typeLine) {
        this.face.setTypeLine(Validate.notBlank(typeLine, "O tipo da face não pode estar em branco"));
        return this;
    }

    public FaceBuilder setToughness(String toughness) {
        this.face.setToughness(toughness);
        return this;
    }

    public FaceBuilder setPower(String power) {
        this.face.setPower(power);
        return this;
    }

    public FaceBuilder setLoyalty(String loyalty) {
        this.face.setLoyalty(loyalty);
        return this;
    }

    public FaceBuilder setOracleText(String oracleText) {
        this.face.setOracleText(oracleText);
        return this;
    }

    public FaceBuilder setManaCost(String manaCost) {
        this.face.setManaCost(manaCost);
        return this;
    }

    public FaceBuilder setImageUri(String imageUri) {
        this.face.setImageUri(imageUri);
        return this;
    }

    public Face build() {
        return this.face;
    }
}
