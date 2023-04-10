package nl.pokemon.game.model.SQMObjects._100_199_walk;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class Gravel_100 extends BaseSQM {

    ImageIcon icon;

    public Gravel_100() {
        this.icon = new ImageIcon("src/main/resources/images/walk/gravel.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 100;
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
