package nl.pokemon.game.service;

import nl.pokemon.game.controller.RpgController;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.ViewSQM;
import nl.pokemon.game.util.FDMapToSQM;
import nl.pokemon.game.util.FullDataMap;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ViewService {

    private BaseSQM[][] viewMap;
    private final int MAX_X = 21;
    private final int MAX_Y = 21;

    private final int speedPixelPerIterate = 2; // 50 = max
    private final int speedTimerDelay = 5;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    SQMService sqmService;

    @Inject
    RpgController controller;

    @Inject
    PlayerService playerService;

    @Inject
    PortalService portalService;

    @Inject
    CurrentPlayer player;

    private void init() {
        FullDataMap.getFDMap();
        viewMap = new BaseSQM[MAX_Y][MAX_X];

        StringBuilder sb = new StringBuilder();

        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                sb.append(viewY+1).append("-").append(viewX+1);
                BaseSQM sqm = Kickstarter.getInstanceOf(ViewSQM.class, sb.toString());
                sqm.setPixelPosXByIndex(viewX);
                sqm.setPixelPosYByIndex(viewY);
                sqm.updateBounds();

                viewMap[viewY][viewX] = sqm;
                sb.setLength(0); // clear stringbuilder
            }
        }
    }

    public void updateView() {

        int START_X_FDM = player.getFDMIndexX() - ((MAX_X - 1)/2);
        int START_Y_FDM = player.getFDMIndexY() - ((MAX_Y - 1)/2);

        for (int y = 0, FDMCurrentPosY = START_Y_FDM; y < MAX_Y; y++, FDMCurrentPosY++) {
            int FDMCurrentPosX = START_X_FDM;

            for (int x = 0; x < MAX_X; x++, FDMCurrentPosX++) {

                BaseSQM sqm = viewMap[y][x];
                sqm = sqmService.insertFDMPositionToSQM(sqm, FDMCurrentPosX, FDMCurrentPosY);
                sqm.updateBounds();

            }
        }
    }

    public void startViewXYSmoothWalking(Direction direction) {

         if (sqmService.isNotWalkable(direction)) {
             controller.setNotMoving(true);
             return;
         }

        setSmoothPosYXByDirection(direction);
        playerService.setXYByDirection(direction);

        AtomicReference<Direction> lastDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);

        Timer smoothMoving = new Timer(speedTimerDelay, e -> {
            for (int y = 0; y < MAX_Y; y++) {
                for (int x = 0; x < MAX_X; x++) {
                    BaseSQM sqm = viewMap[y][x];
                    int newPixelPosX = sqm.getPixelPosXByIndex() + smoothMovePosX;
                    int newPixelPosY = sqm.getPixelPosYByIndex() + smoothMovePosY;

                    sqm.setPixelPosX(newPixelPosX);
                    sqm.setPixelPosY(newPixelPosY);
                    sqm.updateBounds();
                }
            }

            if (pixelCounter.getAndIncrement() >= (ViewSQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {

                if (portalService.isPortalAndTeleport() || controller.getMoveStack().isEmpty()) {
                    endViewXYSmoothWalking(lastDirection, e);
                } else {

                    Direction nextDirection = controller.getMoveStack().pop();
                    if (sqmService.isNotWalkable(nextDirection)) {
                        endViewXYSmoothWalking(lastDirection, e);
                    } else {
                        sqmService.getBaseSQMfromDirection(nextDirection);
                        pixelCounter.set(0);
                        setViewToDefaultXY();
                        updateView();
                        lastDirection.set(nextDirection);
                        setSmoothPosYXByDirection(lastDirection.get());
                        playerService.setXYByDirection(lastDirection.get());
                    }
                }
            }
        });
        smoothMoving.start();
    }

    private void endViewXYSmoothWalking(AtomicReference<Direction> lastDirection, ActionEvent e) {
        Timer timer = (Timer) e.getSource();
        timer.stop();
        controller.setNotMoving(true);
        setViewToDefaultXY();
        updateView();
        player.standStill(lastDirection.get());
    }

    private void setViewToDefaultXY() {
        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                BaseSQM sqm = viewMap[viewY][viewX];
                sqm.setPixelPosXByIndex(viewX);
                sqm.setPixelPosYByIndex(viewY);
            }
        }
    }

    private void setSmoothPosYXByDirection(Direction direction) {
        smoothMovePosX = 0;
        smoothMovePosY = 0;

        switch (direction) {
            case NORTH -> {
                smoothMovePosY = speedPixelPerIterate;
            }
            case EAST -> {
                smoothMovePosX = -speedPixelPerIterate;
            }
            case SOUTH -> {
                smoothMovePosY = -speedPixelPerIterate;
            }
            case WEST -> {
                smoothMovePosX = speedPixelPerIterate;
            }
        }
        player.moveDirection(direction);
    }

    public BaseSQM[][] getViewMap() {
        return viewMap;
    }

    public int getMAX_X() {
        return MAX_X;
    }

    public int getMAX_Y() {
        return MAX_Y;
    }
}
