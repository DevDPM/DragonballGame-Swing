package nl.pokemon.mapGenerator.model;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.View.ViewMap;
import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.SQMs.MG_BaseSQM;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import nl.pokemon.mapGenerator.view.OptionPanel;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Editable_SQM extends MG_BaseSQM {

    public Editable_SQM() {
        this.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OptionPanel optionPanel = Kickstarter.getInstanceOf(OptionPanel.class);
        Editable_SQM editableSqm = (Editable_SQM) e.getSource();
        editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
        editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
        editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
        editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
        this.updateSQM();
    }
}
