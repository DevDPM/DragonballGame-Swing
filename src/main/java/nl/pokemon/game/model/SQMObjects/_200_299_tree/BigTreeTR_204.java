package nl.pokemon.game.model.SQMObjects._200_299_tree;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BaseSQM;

import javax.swing.*;

public class BigTreeTR_204 extends BaseSQM {

    ImageIcon icon;

    public BigTreeTR_204() {
        this.icon = new ImageIcon("src/main/resources/images/bigTree/bigTree-TR.jpg");
    }

    @Override
    public ImageIcon getImageIcon() {
        return this.icon;
    }

    @Override
    public int getObjectNumber() {
        return 204;
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
