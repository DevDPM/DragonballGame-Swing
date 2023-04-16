package nl.pokemon.game.model;

import javax.swing.*;
import java.awt.*;

public class CurrentPlayer extends JLabel {

    int x = 380;
    int y = 330;

    int FDMIndexZ = -1;
    int FDMIndexX = 3;
    int FDMIndexY = 3;

    public CurrentPlayer() {
        this.setBounds(x, y,50,50);
        this.setBackground(Color.CYAN);
        ImageIcon icon = new ImageIcon("src/main/resources/images/userWalk/stand-south.png");
        this.setIcon(icon);
        this.setVisible(true);
    }

    public int getFDMIndexZ() {
        return FDMIndexZ;
    }

    public void setFDMIndexZ(int FDMIndexZ) {
        this.FDMIndexZ = FDMIndexZ;
    }

    public int getFDMIndexX() {
        return FDMIndexX;
    }

    public void setFDMIndexX(int FDMIndexX) {
        this.FDMIndexX = FDMIndexX;
    }

    public int getFDMIndexY() {
        return FDMIndexY;
    }

    public void setFDMIndexY(int FDMIndexY) {
        this.FDMIndexY = FDMIndexY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
