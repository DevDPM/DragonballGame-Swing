package nl.pokemon.game.model.SQMObjects._400_499_obstacle;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.enums.Destination;
import nl.pokemon.game.model.BasePortalSQM;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class Bush_400 extends BaseSQM {


    ImageIcon icon;

    public Bush_400() {
        this.icon = new ImageIcon("src/main/resources/images/obstacles/bush.png");
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
        return 400;
    }

}
