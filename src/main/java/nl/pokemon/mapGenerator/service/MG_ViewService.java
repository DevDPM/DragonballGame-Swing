package nl.pokemon.mapGenerator.service;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.util.FullDataMap;
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
    private final int MAX_X = 60;
    private final int MAX_Y = 60;

    @Inject
    MG_SQMService MGSqmService;

    @Inject
    MG_Controller controller;

    private void init() {
        FullDataMap.getFDMap();
        viewMap = new MG_BaseSQM[MAX_Y][MAX_X];

        StringBuilder sb = new StringBuilder();

        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                sb.append(viewY+1).append("-").append(viewX+1);
                MG_BaseSQM sqm = new Editable_SQM();
                sqm.setPixelPosXByIndex(viewX);
                sqm.setPixelPosYByIndex(viewY);
                sqm.addActionListener(sqm);
                sqm.updateBounds();

                viewMap[viewY][viewX] = sqm;
                sb.setLength(0); // clear stringbuilder
            }
        }
    }

    public MG_BaseSQM[][] getViewMap() {
        return this.viewMap;
    }

    public void updateView() {

//        for (int y = 0, FDMCurrentPosY = START_Y_FDM; y < MAX_Y; y++, FDMCurrentPosY++) {
//            int FDMCurrentPosX = START_X_FDM;
//
//            for (int x = 0; x < MAX_X; x++, FDMCurrentPosX++) {
//
//                BaseSQM sqm = viewMap[y][x];
//                sqm = MGSqmService.insertFDMPositionToSQM(sqm, FDMCurrentPosX, FDMCurrentPosY);
//                sqm.updateBounds();
//
//            }
//        }
    }

    public void startViewXYSmoothWalking(Direction direction) {
//
//        if (sqmService.isNotWalkable(direction)) {
//            controller.setNotMoving(true);
//            return;
//        }
//
//        setSmoothPosYXByDirection(direction);
//        playerService.setXYByDirection(direction);
//
//        AtomicReference<Direction> lastDirection = new AtomicReference<>(direction);
//        AtomicInteger pixelCounter = new AtomicInteger(0);
//
//        Timer smoothMoving = new Timer(speedTimerDelay, e -> {
//            for (int y = 0; y < MAX_Y; y++) {
//                for (int x = 0; x < MAX_X; x++) {
//                    BaseSQM sqm = viewMap[y][x];
//                    int newPixelPosX = sqm.getPixelPosXByIndex() + smoothMovePosX;
//                    int newPixelPosY = sqm.getPixelPosYByIndex() + smoothMovePosY;
//
//                    sqm.setPixelPosX(newPixelPosX);
//                    sqm.setPixelPosY(newPixelPosY);
//                    sqm.updateBounds();
//                }
//            }
//
//            if (pixelCounter.getAndIncrement() >= (ViewSQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {
//
//                if (portalService.isPortalAndTeleport() || controller.getMoveStack().isEmpty()) {
//                    endViewXYSmoothWalking(lastDirection, e);
//                } else {
//
//                    Direction nextDirection = controller.getMoveStack().pop();
//                    if (sqmService.isNotWalkable(nextDirection)) {
//                        endViewXYSmoothWalking(lastDirection, e);
//                    } else {
//                        sqmService.getBaseSQMfromDirection(nextDirection);
//                        pixelCounter.set(0);
//                        setViewToDefaultXY();
//                        updateView();
//                        lastDirection.set(nextDirection);
//                        setSmoothPosYXByDirection(lastDirection.get());
//                        playerService.setXYByDirection(lastDirection.get());
//                    }
//                }
//            }
//        });
//        smoothMoving.start();
    }

    private void endViewXYSmoothWalking(AtomicReference<Direction> lastDirection, ActionEvent e) {
//        Timer timer = (Timer) e.getSource();
//        timer.stop();
//        controller.setNotMoving(true);
//        setViewToDefaultXY();
//        updateView();
//        player.standStill(lastDirection.get());
    }

    private void setViewToDefaultXY() {
//        for (int viewY = 0; viewY < MAX_Y; viewY++) {
//            for (int viewX = 0; viewX < MAX_X; viewX++) {
//                BaseSQM sqm = viewMap[viewY][viewX];
//                sqm.setPixelPosXByIndex(viewX);
//                sqm.setPixelPosYByIndex(viewY);
//            }
//        }
    }

}
