package nl.pokemon.game.rpg.model.obstacle.bigTree;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class BigTreeLR extends BaseSQM {

    ImageIcon icon;

    public BigTreeLR() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-LR.jpg");
    }

    @Override
    public void loadNewImageIcon(ImageIcon icon) {
        this.setIcon(icon);
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }
}
