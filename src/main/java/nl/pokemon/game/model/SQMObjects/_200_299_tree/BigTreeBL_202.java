package nl.pokemon.game.model.SQMObjects._200_299_tree;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.Combinable;

import javax.swing.*;

public class BigTreeBL_202 extends BaseSQM implements Combinable {

    ImageIcon icon;
    int sqmWidth = 2;
    int sqmHeight = 2;

    public BigTreeBL_202() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-LL.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 202;
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
