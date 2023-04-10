package nl.pokemon.game.model.SQMObjects._200_299_tree;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeBL_200 extends BaseSQM {

    ImageIcon icon;

    public BigTreeBL_200() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-LL.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 200;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public Classify getClassify() {
        return Classify.TREE;
    }
}
