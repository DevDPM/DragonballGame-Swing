package nl.pokemon.game.model.walk;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.service.Direction;

import javax.swing.*;

public class Gravel extends BaseSQM {

    ImageIcon icon;

    public Gravel() {
        this.icon = new ImageIcon("src/main/resources/images/walk/gravel.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public boolean isNotWalkable() {
        return false;
    }
}
