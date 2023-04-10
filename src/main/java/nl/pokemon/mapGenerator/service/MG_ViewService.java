package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.mapGenerator.controller.MG_Controller;
import nl.pokemon.mapGenerator.model.Editable_SQM;
import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MG_ViewService {

    private MG_BaseSQM[][] viewMap;
    private final int MAX_X = 50;
    private final int MAX_Y = 50;

    public MG_ViewService() {
        viewMap = new MG_BaseSQM[MAX_Y][MAX_X];
        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                MG_BaseSQM sqm = new Editable_SQM(viewX, viewY);
                sqm.addActionListener(sqm);
                sqm.updateBounds();

                viewMap[viewY][viewX] = sqm;
            }
        }
    }

    public MG_BaseSQM[][] getViewMap() {
        return this.viewMap;
    }

    public void moveView(Direction direction) {

        int incrementIndexX = 0;
        int incrementIndexY = 0;

        switch (direction) {
            case NORTH -> incrementIndexY++;
            case EAST -> incrementIndexX--;
            case SOUTH -> incrementIndexY--;
            case WEST -> incrementIndexX++;
        }

        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                MG_BaseSQM sqm = viewMap[viewY][viewX];
                sqm.setPixelPosXByIndex(sqm.getIndexX() + incrementIndexX);
                sqm.setPixelPosYByIndex(sqm.getIndexY() + incrementIndexY);
                sqm.updateBounds();
            }
        }
    }
}