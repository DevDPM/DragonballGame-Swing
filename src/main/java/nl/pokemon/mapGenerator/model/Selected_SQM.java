package nl.pokemon.mapGenerator.model;

import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.mapGenerator.view.SelectedPanel;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Selected_SQM extends JButton implements ActionListener {

    private int objectId;
    private ImageIcon icon;
    private BaseSQM baseSQM;


    public Selected_SQM(int sqmId, BaseSQM baseSQM) {
        this.icon = baseSQM.getImageIcon();
        this.objectId = sqmId;
        this.setIcon(icon);
    }

    public BaseSQM getBaseSQM() {
        return baseSQM;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Selection selection = Kickstarter.getInstanceOf(Selection.class, "selectedItem");
        selection.setId(objectId);
        selection.setImageIcon(icon);
        selection.setBaseSQM(baseSQM);
//        Kickstarter.getInstanceOf(SelectedPanel.class).updateSelection();
    }

    public int getObjectId() {
        return objectId;
    }
}
