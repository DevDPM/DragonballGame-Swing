package nl.pokemon.game.model.core.model.characters;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;

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
