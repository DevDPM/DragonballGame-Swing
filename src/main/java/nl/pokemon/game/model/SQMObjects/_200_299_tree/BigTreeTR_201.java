package nl.pokemon.game.model.SQMObjects._200_299_tree;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.Collectable;

import javax.swing.*;

public class BigTreeTR_201 extends BaseSQM implements Collectable {

    ImageIcon icon;
    int sqmWidth = 2;
    int sqmHeight = 2;

    public BigTreeTR_201() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TR.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 201;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public Classify getClassify() {
        return Classify.TREE1;
    }

    @Override
    public int getNumberOfSQMWidth() {
        return sqmWidth;
    }

    @Override
    public int getNumberOfSQMHeight() {
        return sqmHeight;
    }

    @Override
    public boolean isTopLeftCornerOfCollection() {
        return false;
    }
}
