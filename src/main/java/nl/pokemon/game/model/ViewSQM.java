package nl.pokemon.game.model;

import javax.swing.*;

public class ViewSQM extends BaseSQM {

    ImageIcon icon;

    public ViewSQM() {
        this.icon = new ImageIcon("src/main/resources/images/walk/grass.jpg");
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
