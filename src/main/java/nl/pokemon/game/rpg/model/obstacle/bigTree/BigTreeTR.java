package nl.pokemon.game.rpg.model.obstacle.bigTree;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class BigTreeTR extends BaseSQM {

    ImageIcon icon;

    public BigTreeTR() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TR.jpg");
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
