package nl.pokemon.game.rpg.model;

import javax.swing.*;

public abstract class BaseSQM extends JLabel {

    int indexX;
    int indexY;

    int pixelX;
    int pixelY;

    public static final int SQM_PIXEL_WIDTH_X = 50;
    public static final int SQM_PIXEL_HEIGHT_Y = 50;

    private static final int OFFSET_PIXEL_X = -120;
    private static final int OFFSET_PIXEL_Y = -120;

    public abstract void loadNewImageIcon(ImageIcon icon);
    public abstract ImageIcon getImageIcon();

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
    }
}
