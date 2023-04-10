package nl.pokemon.game.model.SQMObjects._100_199_walk;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class GrassStairs_103 extends BaseSQM {

    ImageIcon icon;

    public GrassStairs_103() {
        this.icon = new ImageIcon("src/main/resources/images/walk/grass-stairs.png");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 103;
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
