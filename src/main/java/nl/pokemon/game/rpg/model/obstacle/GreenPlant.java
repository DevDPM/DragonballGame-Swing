package nl.pokemon.game.rpg.model.obstacle;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class GreenPlant extends BaseSQM {

    ImageIcon icon;

    public GreenPlant() {
        this.icon = new ImageIcon("src/main/resources/images/plants/plants.jpg");
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
