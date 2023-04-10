package nl.pokemon.game.model.SQMObjects._200_299_tree;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeTL_202 extends BaseSQM {

    ImageIcon icon;

    public BigTreeTL_202() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TL.jpg");
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
        return Classify.TREE;
    }
}
