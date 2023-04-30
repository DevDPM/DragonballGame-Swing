package nl.pokemon.game.model.core.model;

import nl.pokemon.game.enums.AreaType;

public interface Elevatable {

    int elevatingValue();

    static AreaType getAreaType() {
        return AreaType.HIGHER_TERRAIN;
    }
}
