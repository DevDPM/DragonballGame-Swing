package nl.pokemon.mapGenerator.model;

import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class Selection {

    private int id;
    private ImageIcon imageIcon;
    private BaseSQM baseSQM;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public BaseSQM getBaseSQM() {
        return baseSQM;
    }

    public void setBaseSQM(BaseSQM baseSQM) {
        this.baseSQM = baseSQM;
    }
}
