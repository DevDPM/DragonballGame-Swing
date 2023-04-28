package nl.pokemon.game.core.model.characters;

import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.tiles.BaseTile;

public abstract class BaseEntity extends BaseTile {

    private long id;

    public abstract void setWalkingImage(Direction direction);
    public abstract void setStandingImage(Direction direction);
}
