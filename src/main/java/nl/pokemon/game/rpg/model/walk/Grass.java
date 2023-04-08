package nl.pokemon.game.rpg.model.walk;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class Grass extends BaseSQM {

    ImageIcon icon;

    public Grass() {
        this.icon = new ImageIcon("src/main/resources/images/walk/grass.jpg");
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
