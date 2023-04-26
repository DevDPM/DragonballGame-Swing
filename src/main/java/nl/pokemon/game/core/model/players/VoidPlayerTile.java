package nl.pokemon.game.core.model.players;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;

public class VoidPlayerTile extends BaseEntity{

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return null;
    }

    @Override
    public void setWalkingImage(Direction direction) {

    }

    @Override
    public void setStandingImage(Direction direction) {

    }
}
