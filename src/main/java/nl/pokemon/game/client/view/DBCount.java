package nl.pokemon.game.client.view;

import nl.pokemon.game.core.model.tiles.ItemTile;
import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class DBCount extends JLabel {

    public DBCount() {
        this.setVisible(true);
        this.setFocusable(false);
        this.setBounds(0, 0, 500, 50);
    }

    public void addDragonBall(ItemTile dragonBall) {
        JLabel dbImage = new JLabel(dragonBall.getImageIcon());
        dbImage.setVisible(true);
        dbImage.setFocusable(false);
        switch (dragonBall.getSqmId()) {
            case 10 -> dbImage.setBounds(0, 0, 50, 50);
            case 11 -> dbImage.setBounds(50, 0, 50, 50);
            case 12 -> dbImage.setBounds(100, 0, 50, 50);
            case 13 -> dbImage.setBounds(150, 0, 50, 50);
            case 14 -> dbImage.setBounds(200, 0, 50, 50);
            case 15 -> dbImage.setBounds(250, 0, 50, 50);
            case 16 -> dbImage.setBounds(300, 0, 50, 50);
        }
        this.add(dbImage);
        this.repaint();
    }

}
