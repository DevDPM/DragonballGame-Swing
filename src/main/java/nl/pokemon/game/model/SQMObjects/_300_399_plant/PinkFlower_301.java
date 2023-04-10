package nl.pokemon.game.model.SQMObjects._300_399_plant;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class PinkFlower_301 extends BaseSQM {

    ImageIcon icon;

    public PinkFlower_301() {
        this.icon = new ImageIcon("src/main/resources/images/plants/pink-flower.png");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 301;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public Classify getClassify() {
        return Classify.PLANT;
    }
}
