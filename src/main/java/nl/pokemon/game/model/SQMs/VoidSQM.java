package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;

import javax.swing.*;

public class VoidSQM extends BaseSQM {

    public VoidSQM() {
        this.setImageIcon(null);
    }

    @Override
    public int getObjectNumber() {
        return 0;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }
}
