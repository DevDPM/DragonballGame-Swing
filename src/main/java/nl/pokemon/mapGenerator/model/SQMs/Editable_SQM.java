package nl.pokemon.mapGenerator.model.SQMs;

import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
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

        if (editableSqm.getCoordinateX() < 0 || editableSqm.getCoordinateY() < 0 ||
                editableSqm.getCoordinateX() >= Kickstarter.getInstanceOf(MG_ViewMap.class, "fullMap")
                        .getViewMap().get(optionPanel.getCurrentZ())
                        .get(optionPanel.getCurentAreaType())
                        .getGridMap().length ||
                editableSqm.getCoordinateY() >= Kickstarter.getInstanceOf(MG_ViewMap.class, "fullMap")
                        .getViewMap().get(optionPanel.getCurrentZ())
                        .get(optionPanel.getCurentAreaType())
                        .getGridMap()[0].length) {
            return;
        }

        if (optionPanel.isAutoFill()) {
            Kickstarter.getInstanceOf(MG_SQMService.class).performAutoFill(editableSqm);
        } else if (optionPanel.isFillAll()) {
            Kickstarter.getInstanceOf(MG_SQMService.class).performFillAll(editableSqm);
        } else {
            Kickstarter.getInstanceOf(MG_SQMService.class).performTileSwitch(editableSqm);
        }
    }
}
