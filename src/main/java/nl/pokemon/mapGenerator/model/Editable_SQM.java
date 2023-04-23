package nl.pokemon.mapGenerator.model;

import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Editable_SQM extends MG_BaseSQM {

    private ImageIcon icon;

    public Editable_SQM(int x, int y) {
        this.icon = new ImageIcon("src/main/resources/images/emptySpot.png");
        this.setFocusable(false);
        setIdX(x);
        setIdY(y);
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        Kickstarter.getInstanceOf(MG_SQMService.class).updateSQM(this);
    }
}
