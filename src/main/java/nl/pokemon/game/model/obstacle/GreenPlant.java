package nl.pokemon.game.model.obstacle;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.service.Direction;

import javax.swing.*;

public class GreenPlant extends BaseSQM {

    ImageIcon icon;

    public GreenPlant() {
        this.icon = new ImageIcon("src/main/resources/images/plants/plants.jpg");
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
