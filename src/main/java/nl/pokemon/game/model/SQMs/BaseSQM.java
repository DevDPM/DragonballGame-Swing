package nl.pokemon.game.model.SQMs;

import nl.pokemon.game.enums.AreaType;

import javax.swing.*;

public abstract class BaseSQM extends JLabel {

    private int indexX;
    private int indexY;
    private ImageIcon imageIcon;

    private int sqmSizeX = 1;
    private int sqmSizeY = 1;

    private int pixelX;
    private int pixelY;

    public static final int SQM_PIXEL_WIDTH_X = 50;
    public static final int SQM_PIXEL_HEIGHT_Y = 50;

    private int OFFSET_PIXEL_X = -120;
    private int OFFSET_PIXEL_Y = -170;

    public abstract int getObjectNumber();
    public abstract boolean isNotWalkable();
    public abstract AreaType getAreaType();

    public void updateSQM() {
        setPixelPosXByIndex(this.indexX);
        setPixelPosYByIndex(this.indexY);
        this.setIcon(this.imageIcon);
        this.setBounds((this.pixelX + (SQM_PIXEL_WIDTH_X*(1-sqmSizeX))), (this.pixelY + (SQM_PIXEL_HEIGHT_Y*(1-sqmSizeY))), SQM_PIXEL_WIDTH_X*sqmSizeX, (SQM_PIXEL_HEIGHT_Y*sqmSizeY));
    }

    public void moveSQM(int pixelX, int pixelY) {
        setPixelPosX(pixelX);
        setPixelPosY(pixelY);
        this.setBounds((this.pixelX + (SQM_PIXEL_WIDTH_X*(1-sqmSizeX))), (this.pixelY + (SQM_PIXEL_HEIGHT_Y*(1-sqmSizeY))), SQM_PIXEL_WIDTH_X*sqmSizeX, SQM_PIXEL_HEIGHT_Y*sqmSizeY);
    }

    public int getSqmSizeX() {
        return sqmSizeX;
    }

    public int getSqmSizeY() {
        return sqmSizeY;
    }

    public void setSqmSizeX(int sqmSizeX) {
        this.sqmSizeX = sqmSizeX;
    }

    public void setSqmSizeY(int sqmSizeY) {
        this.sqmSizeY = sqmSizeY;
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

    public void setOFFSET_PIXEL_X(int OFFSET_PIXEL_X) {
        this.OFFSET_PIXEL_X = OFFSET_PIXEL_X;
    }

    public void setOFFSET_PIXEL_Y(int OFFSET_PIXEL_Y) {
        this.OFFSET_PIXEL_Y = OFFSET_PIXEL_Y;
    }
}
