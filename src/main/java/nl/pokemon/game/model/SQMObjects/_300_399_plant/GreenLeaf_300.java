package nl.pokemon.game.model.SQMObjects._300_399_plant;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class GreenLeaf_300 extends BaseSQM {

    ImageIcon icon;

    public GreenLeaf_300() {
        this.icon = new ImageIcon("src/main/resources/images/plants/plants.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 300;
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
