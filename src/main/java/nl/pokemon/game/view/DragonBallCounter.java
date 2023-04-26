package nl.pokemon.game.view;

import nl.pokemon.game.model.SQMs.ItemSQM;

import javax.swing.*;

public class DragonBallCounter extends JLabel {

    public DragonBallCounter() {
        this.setVisible(true);
        this.setFocusable(false);
        this.setBounds(0, 0, 500, 50);
    }

    public void addDragonBall(ItemSQM dragonBall) {
        JLabel dbImage = new JLabel(dragonBall.getImageIcon());
        dbImage.setVisible(true);
        dbImage.setFocusable(false);
        switch (dragonBall.getSqmId()) {
            case 41 -> dbImage.setBounds(0, 0, 50, 50);
            case 42 -> dbImage.setBounds(50, 0, 50, 50);
            case 43 -> dbImage.setBounds(100, 0, 50, 50);
            case 44 -> dbImage.setBounds(150, 0, 50, 50);
            case 45 -> dbImage.setBounds(200, 0, 50, 50);
            case 46 -> dbImage.setBounds(250, 0, 50, 50);
            case 47 -> dbImage.setBounds(300, 0, 50, 50);
        }
        this.add(dbImage);
        this.repaint();
    }

}
