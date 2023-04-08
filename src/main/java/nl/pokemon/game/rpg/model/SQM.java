package nl.pokemon.game.rpg.model;

import javax.swing.*;

public class SQM extends BaseSQM {

    ImageIcon icon;

    public SQM() {
        this.setVisible(true);
    }

    @Override
    public void loadNewImageIcon(ImageIcon icon) {
        this.icon = icon;
        this.setIcon(icon);
        this.setVisible(true);
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }
}
