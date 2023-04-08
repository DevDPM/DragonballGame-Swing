package nl.pokemon.game.rpg.model.obstacle.bigTree;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class BigTreeLL extends BaseSQM {

    ImageIcon icon;

    public BigTreeLL() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-LL.jpg");
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
