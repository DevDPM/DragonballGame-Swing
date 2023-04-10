package nl.pokemon.game.model.SQMObjects._400_499_obstacle;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class CliffGravel_401 extends BaseSQM {


    ImageIcon icon;

    public CliffGravel_401() {
        this.icon = new ImageIcon("src/main/resources/images/obstacles/cliff-gravel.png");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public Classify getClassify() {
        return Classify.OBSTACLE;
    }

    @Override
    public int getObjectNumber() {
        return 401;
    }

}
