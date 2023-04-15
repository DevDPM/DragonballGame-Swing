package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Classify;

import javax.swing.*;

public class VoidSQM extends BaseSQM {

    public VoidSQM() {
        this.setImageIcon(new ImageIcon("src/main/resources/images/emptySpot.png"));
    }

    @Override
    public int getObjectNumber() {
        return 0;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public Classify getClassify() {
        return Classify.OBSTACLE;
    }

    @Override
    public AreaType getAreaType() {
        return AreaType.MAP;
    }
}
