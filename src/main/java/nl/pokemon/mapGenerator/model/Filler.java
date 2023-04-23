package nl.pokemon.mapGenerator.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filler extends JButton implements ActionListener {
    private boolean incrementFill;

    public boolean isIncrementFill() {
        return incrementFill;
    }

    public void setIncrementFill() {
        this.incrementFill = !this.incrementFill;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setIncrementFill();
        this.setText("Till tiles 3x3: " + isIncrementFill());
    }
}
