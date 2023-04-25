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

    @Inject(name = "fullMap")
    MG_ViewMap fullMap;

    @Inject(name = "clientViewMap")
    MG_ViewMap clientViewMap;

    @Inject
    OptionPanel optionPanel;

    public void performAutoFill(Editable_SQM sqm) {
        MG_BaseSQM[][] storedSQMs = fullMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        MG_BaseSQM[][] clientViewSQMs = clientViewMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        int currentX = sqm.getCoordinateX();
        int currentY = sqm.getCoordinateY();

        int minX = currentX-1;
        int maxX = currentX+1;
        int minY = currentY-1;
        int maxY = currentY+1;

        int clientX = sqm.getIndexX();
        int clientY = sqm.getIndexY();

        int minViewX = clientX-1;
        int maxViewX = clientX+1;
        int minViewY = clientY-1;
        int maxViewY = clientY+1;

        if (minX >= 0 && maxX <= storedSQMs.length && minY >= 0 && maxY <= storedSQMs[0].length) {

            for (int storedY = minY, viewY = minViewY; storedY <= maxY && viewY <= maxViewY; storedY++, viewY++) {
                for (int storedX = minX, viewX = minViewX; storedX <= maxX && viewX <= maxViewX; storedX++, viewX++) {

                    MG_BaseSQM editableSqm = storedSQMs[storedY][storedX];
                    MG_BaseSQM clientViewSQM = clientViewSQMs[viewY][viewX];

                    editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
                    editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                    editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                    editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                    editableSqm.updateSQM();

                    clientViewSQM.setSqmId(optionPanel.getLastSelectedSqmId());
                    clientViewSQM.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                    clientViewSQM.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                    clientViewSQM.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                    clientViewSQM.updateSQM();
                }
            }
        }
    }

    public void performFillAll(Editable_SQM sqm) {
        MG_BaseSQM[][] baseSQMS = fullMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();

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

        MG_BaseSQM[][] storedSQMs = fullMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        MG_BaseSQM[][] clientViewSQMs = clientViewMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        int currentX = sqm.getCoordinateX();
        int currentY = sqm.getCoordinateY();

        int minX = currentX-1;
        int maxX = currentX+1;
        int minY = currentY-1;
        int maxY = currentY+1;

        if (minX >= 0 && maxX <= storedSQMs.length && minY >= 0 && maxY <= storedSQMs[0].length) {

            for (int y = 0; y < baseSQMS.length; y++) {
                for (int x = 0; x < baseSQMS[0].length; x++) {

                    MG_BaseSQM editableSqm = storedSQMs[y][x];

                    editableSqm.setSqmId(optionPanel.getLastSelectedSqmId());
                    editableSqm.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                    editableSqm.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                    editableSqm.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                    editableSqm.updateSQM();

                    if (x <= 30 && y <= 30) {
                        MG_BaseSQM clientViewSQM = clientViewSQMs[y][x];
                        clientViewSQM.setSqmId(optionPanel.getLastSelectedSqmId());
                        clientViewSQM.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
                        clientViewSQM.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
                        clientViewSQM.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
                        clientViewSQM.updateSQM();
                    }
                }
            }
        }

    }

    public void performTileSwitch(Editable_SQM editableSqm) {

        MG_BaseSQM[][] storedSQMs = fullMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        MG_BaseSQM[][] clientViewSQMs = clientViewMap.getViewMap().get(optionPanel.getCurrentZ()).get(optionPanel.getCurentAreaType()).getGridMap();
        int currentX = editableSqm.getCoordinateX();
        int currentY = editableSqm.getCoordinateY();

        int clientX = editableSqm.getIndexX();
        int clientY = editableSqm.getIndexY();

        if (currentX >= 0 && currentX <= storedSQMs.length && currentY >= 0 && currentY <= storedSQMs[0].length) {

            MG_BaseSQM storedSQM = storedSQMs[currentY][currentX];
            MG_BaseSQM clientViewSQM = clientViewSQMs[clientY][clientX];

            storedSQM.setSqmId(optionPanel.getLastSelectedSqmId());
            storedSQM.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
            storedSQM.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
            storedSQM.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
            storedSQM.updateSQM();

            clientViewSQM.setSqmId(optionPanel.getLastSelectedSqmId());
            clientViewSQM.setImageIcon(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getImageIcon());
            clientViewSQM.setSqmSizeX(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeX());
            clientViewSQM.setSqmSizeY(TilesetImageContainer.getSQMByIntAndArea(optionPanel.getCurentAreaType(), optionPanel.getLastSelectedSqmId()).getSqmSizeY());
            clientViewSQM.updateSQM();
        }
    }
}
