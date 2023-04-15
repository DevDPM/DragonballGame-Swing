package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.enums.AreaType;

import javax.swing.*;

public abstract class BaseSQM extends JLabel {

    private int indexX;
    private int indexY;
    private ImageIcon imageIcon;

    int pixelX;
    int pixelY;

    public static final int SQM_PIXEL_WIDTH_X = 50;
    public static final int SQM_PIXEL_HEIGHT_Y = 50;

    private static final int OFFSET_PIXEL_X = -120;
    private static final int OFFSET_PIXEL_Y = -170;

    public abstract int getObjectNumber();
    public abstract boolean isNotWalkable();
    public abstract Classify getClassify();
    public abstract AreaType getAreaType();

    public void updateSQM() {
        setPixelPosXByIndex(this.indexX);
        setPixelPosYByIndex(this.indexY);
        this.setIcon(this.imageIcon);
        this.setBounds(this.pixelX, this.pixelY, SQM_PIXEL_WIDTH_X, SQM_PIXEL_HEIGHT_Y);
        this.setVisible(true);
    }

    public void moveSQM(int pixelX, int pixelY) {
        setPixelPosX(pixelX);
        setPixelPosY(pixelY);
        this.setBounds(pixelX, pixelY, SQM_PIXEL_WIDTH_X, SQM_PIXEL_HEIGHT_Y);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public void setPixelPosXByIndex(int index) {
        this.pixelX = (index * SQM_PIXEL_WIDTH_X) + OFFSET_PIXEL_X;
    }

    public void setPixelPosYByIndex(int index) {
        this.pixelY = (index * SQM_PIXEL_HEIGHT_Y) + OFFSET_PIXEL_Y;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
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
}
