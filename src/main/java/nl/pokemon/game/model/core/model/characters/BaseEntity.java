package nl.pokemon.game.model.core.model.characters;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.core.model.tiles.BaseTile;

public abstract class BaseEntity extends BaseTile {

    private long id;

    public abstract void setWalkingImage(Direction direction);
    public abstract void setStandingImage(Direction direction);
}
