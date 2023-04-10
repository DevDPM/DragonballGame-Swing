package nl.pokemon.game.model.SQMObjects._400_499_obstacle;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class WoodenBlock_404 extends BaseSQM {


    ImageIcon icon;

    public WoodenBlock_404() {
        this.icon = new ImageIcon("src/main/resources/images/obstacles/wooden-block.png");
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
        return 404;
    }

}
