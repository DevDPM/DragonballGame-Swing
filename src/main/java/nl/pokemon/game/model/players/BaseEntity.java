package nl.pokemon.game.model.players;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.SQMs.BaseSQM;

import javax.swing.*;

public abstract class BaseEntity extends BaseSQM {

    private long id;

    public abstract void setWalkingImage(Direction direction);
    public abstract void setStandingImage(Direction direction);
}
