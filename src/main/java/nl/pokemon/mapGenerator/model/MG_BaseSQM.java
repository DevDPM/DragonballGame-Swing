package nl.pokemon.mapGenerator.model;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class MG_BaseSQM extends JButton implements ActionListener {

    int idX;
    int idY;

    int indexX;
    int indexY;

    int pixelX;
    int pixelY;

    ImageIcon imageIcon;

    int objectNumber = 0;

    public static final int SQM_PIXEL_WIDTH_X = 50;
    public static final int SQM_PIXEL_HEIGHT_Y = 50;

    private static final int OFFSET_PIXEL_X = 0;
    private static final int OFFSET_PIXEL_Y = 0;

    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    public void restrictedSQM() {

    }

    public int getIdX() {
        return idX;
    }

    public void setIdX(int idX) {
        this.idX = idX;
        this.indexX = idX;
        this.pixelX = (idX * SQM_PIXEL_WIDTH_X) + OFFSET_PIXEL_X;
    }

    public int getIdY() {
        return idY;
    }

    public void setIdY(int idY) {
        this.idY = idY;
        this.indexY = idY;
        this.pixelY = (idY * SQM_PIXEL_HEIGHT_Y) + OFFSET_PIXEL_Y;
    }

    public void setObjectNumber(int number) {
        this.objectNumber = number;
    }

    public int getObjectNumber() {
        return this.objectNumber;
    }

    public void setImageIcon(ImageIcon icon) {
        this.imageIcon = icon;
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
        this.setImageIcon(this.imageIcon);
        this.setVisible(true);
    }



}
