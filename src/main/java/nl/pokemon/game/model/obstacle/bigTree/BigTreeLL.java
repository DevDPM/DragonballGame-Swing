package nl.pokemon.game.model.obstacle.bigTree;

import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeLL extends BaseSQM {

    ImageIcon icon;

    public BigTreeLL() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-LL.jpg");
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
