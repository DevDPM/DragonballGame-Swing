package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.SQMs.Editable_SQM;
import nl.pokemon.mapGenerator.model.SQMs.MG_BaseSQM;
import nl.pokemon.mapGenerator.model.View.MG_ViewMap;
import nl.pokemon.mapGenerator.view.OptionPanel;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class MG_SQMService {

    @Inject
    MG_ViewMap viewMap;

    @Inject
    OptionPanel optionPanel;

    public void performAutoFill(Editable_SQM sqm) {
        MG_BaseSQM[][] baseSQMS = viewMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        int currentX = sqm.getIndexX();
        int currentY = sqm.getIndexY();

        int minX = currentX-1;
        int maxX = currentX+1;
        int minY = currentY-1;
        int maxY = currentY+1;

        if (minX >= 0 && maxX <= baseSQMS.length &&
                minY >= 0 && maxY <= baseSQMS[0].length) {


            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    MG_BaseSQM editableSqm = baseSQMS[y][x];
                    editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
                    editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                    editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                    editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                    editableSqm.updateSQM();
                }
            }
        }
    }

    public void performFillAll(Editable_SQM sqm) {
        MG_BaseSQM[][] baseSQMS = viewMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();

        for (int y = 0; y < baseSQMS.length; y++) {
            for (int x = 0; x < baseSQMS[0].length; x++) {
                MG_BaseSQM editableSqm = baseSQMS[y][x];
                editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
                editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                editableSqm.updateSQM();
            }
        }
    }
}
