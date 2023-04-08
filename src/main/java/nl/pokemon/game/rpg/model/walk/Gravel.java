package nl.pokemon.game.rpg.model.walk;

import nl.pokemon.game.rpg.model.BaseSQM;

import javax.swing.*;

public class Gravel extends BaseSQM {

    ImageIcon icon;

    public Gravel() {
        this.icon = new ImageIcon("src/main/resources/images/walk/gravel.jpg");
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
