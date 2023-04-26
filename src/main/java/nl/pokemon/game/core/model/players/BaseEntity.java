package nl.pokemon.game.core.model.players;

import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.Tiles.BaseTile;

public abstract class BaseEntity extends BaseTile {

    private long id;

    public abstract void setWalkingImage(Direction direction);
    public abstract void setStandingImage(Direction direction);
}
