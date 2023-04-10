package nl.pokemon.game.model.SQMObjects._100_199_walk;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class Grass_102 extends BaseSQM {

    ImageIcon icon;

    public Grass_102() {
        this.icon = new ImageIcon("src/main/resources/images/walk/green-grass.png");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 102;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public Classify getClassify() {
        return Classify.WALK;
    }
}
