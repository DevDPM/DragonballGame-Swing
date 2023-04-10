package nl.pokemon.mapGenerator.model;

import nl.pokemon.game.model.Walkable;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class MG_BaseSQM extends JButton implements ActionListener {

    int indexX;
    int indexY;

    int pixelX;
    int pixelY;

    public static final int SQM_PIXEL_WIDTH_X = 50;
    public static final int SQM_PIXEL_HEIGHT_Y = 50;

    private static final int OFFSET_PIXEL_X = 0;
    private static final int OFFSET_PIXEL_Y = 0;


    public abstract ImageIcon getImageIcon();

    public void restrictedSQM() {

    }



    public void loadNewImageIcon(ImageIcon icon) {
        this.setIcon(icon);
    }

    public void setPixelPosXByIndex(int index) {
        this.indexX = index;
        this.pixelX = (index * SQM_PIXEL_WIDTH_X) + OFFSET_PIXEL_X;
    }

    public void setPixelPosYByIndex(int index) {
        this.indexY = index;
        this.pixelY = (index * SQM_PIXEL_HEIGHT_Y) + OFFSET_PIXEL_Y;
    }

    public int getPixelPosXByIndex() {
        return this.pixelX;
    }

    public int getPixelPosYByIndex() {
        return this.pixelY;
    }

    public void setPixelPosX(int pixel) {
        this.pixelX = pixel;
    }

    public void setPixelPosY(int pixel) {
        this.pixelY = pixel;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    @Override
    public int getX() {
        return pixelX;
    }

    @Override
    public int getY() {
        return pixelY;
    }

    public void updateBounds() {
        this.setBounds(this.pixelX, this.pixelY, SQM_PIXEL_WIDTH_X, SQM_PIXEL_HEIGHT_Y);
        this.loadNewImageIcon(this.getImageIcon());
        this.setVisible(true);
    }


}