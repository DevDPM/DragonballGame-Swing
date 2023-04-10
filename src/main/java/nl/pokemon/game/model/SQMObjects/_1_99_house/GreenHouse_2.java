package nl.pokemon.game.model.SQMObjects._1_99_house;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.Combinable;

import javax.swing.*;

public class GreenHouse_2 extends BaseSQM implements Combinable {

    ImageIcon icon;

    public GreenHouse_2() {
        this.icon = new ImageIcon("src/main/resources/images/houses/green-house/green-TL2.png");
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }

    @Override
    public int getObjectNumber() {
        return 2;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public Classify getClassify() {
        return Classify.GREEN_HOUSE;
    }

    @Override
    public int getNumberOfSQMWidth() {
        return 4;
    }

    @Override
    public int getNumberOfSQMHeight() {
        return 2;
    }

    @Override
    public boolean isTopLeftCornerOfCollection() {
        return false;
    }
}
