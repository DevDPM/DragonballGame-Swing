package nl.pokemon.mapGenerator.model.SQMs;

import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.service.MG_SQMService;
import nl.pokemon.mapGenerator.view.OptionPanel;
import org.dpmFramework.Kickstarter;

import java.awt.event.ActionEvent;

public class Editable_SQM extends MG_BaseSQM {

    public Editable_SQM() {
        this.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OptionPanel optionPanel = Kickstarter.getInstanceOf(OptionPanel.class);
        Editable_SQM editableSqm = (Editable_SQM) e.getSource();

        if (optionPanel.isAutoFill()) {
            Kickstarter.getInstanceOf(MG_SQMService.class).performAutoFill(editableSqm);
        } else if (optionPanel.isFillAll()) {
            Kickstarter.getInstanceOf(MG_SQMService.class).performFillAll(editableSqm);
        } else {
            editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
            editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
            editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
            editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
            this.updateSQM();
        }
    }
}
