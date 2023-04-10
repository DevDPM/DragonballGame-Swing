package nl.pokemon.mapGenerator.model;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Editable_SQM extends MG_BaseSQM {

    ImageIcon icon;

    public Editable_SQM() {
        this.icon = new ImageIcon("src/main/resources/images/walk/grass.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test");
    }
}
