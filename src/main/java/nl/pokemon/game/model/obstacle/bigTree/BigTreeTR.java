package nl.pokemon.game.model.obstacle.bigTree;

import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeTR extends BaseSQM {

    ImageIcon icon;

    public BigTreeTR() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TR.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }
}
