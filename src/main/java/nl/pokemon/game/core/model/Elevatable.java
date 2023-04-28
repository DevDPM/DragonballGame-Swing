package nl.pokemon.game.core.model;

import nl.pokemon.game.client.enums.AreaType;

public interface Elevatable {

    int elevatingValue();

    static AreaType getAreaType() {
        return AreaType.HIGHER_TERRAIN;
    }
}
