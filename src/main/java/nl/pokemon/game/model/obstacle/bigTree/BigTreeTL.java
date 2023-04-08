package nl.pokemon.game.model.obstacle.bigTree;

import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeTL extends BaseSQM {

    ImageIcon icon;

    public BigTreeTL() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TL.jpg");
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
