package nl.pokemon.game.model.SQMObjects;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class ViewSQM extends BaseSQM {

    ImageIcon icon;

    public ViewSQM() {
        this.icon = new ImageIcon("src/main/resources/images/walk/grass.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
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
}
