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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ViewService {

    private BaseSQM[][] viewMap;
    private final int MAX_X = 21;
    private final int MAX_Y = 21;

    private final int speedPixelPerIterate = 5; // 50 = max
    private final int speedTimerDelay = 5;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    SQMService sqmService;

    @Inject
    RpgController controller;

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

                // check if x/y player position of 20 by 20 view is out of range in FullDataMap array
                if (FDMCurrentPosX < 0 || FDMCurrentPosY < 0 || FDMCurrentPosX > FullDataMap.X_MAX_VALUE()-1 || FDMCurrentPosY > FullDataMap.Y_MAX_VALUE()-1) {
                    continue;
                }

                BaseSQM sqm = viewMap[y][x];
                sqm = insertFDMPositionToSQM(sqm, FDMCurrentPosX, FDMCurrentPosY);
                sqm.updateBounds();

            }
        }
    }

    public void moveViewXYSmoothly(Direction direction) {

         if (sqmService.isNotWalkable(direction)) {
             controller.setNotMoving(true);
             return;
         }

         JFrame frame = sqmService.isPortal(direction);
         if (frame != null) {
             frame.getContentPane();
             frame.setVisible(true);
         }

        setSmoothPosYXByDirection(direction);

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

                if (controller.getMoveStack().isEmpty()) {
                    Timer timer = (Timer) e.getSource();
                    timer.stop();
                    controller.setNotMoving(true);
                    setViewToDefaultXY();
                    updateView();
                    player.standStill(lastDirection.get());
                } else {
                    Direction nextDirection = controller.getMoveStack().pop();
                    if (sqmService.isNotWalkable(nextDirection)) {
                        Timer timer = (Timer) e.getSource();
                        timer.stop();
                        controller.setNotMoving(true);
                        setViewToDefaultXY();
                        updateView();
                        player.standStill(lastDirection.get());
                    } else {
                        sqmService.isPortal(nextDirection);
                        pixelCounter.set(0);
                        setViewToDefaultXY();
                        updateView();
                        lastDirection.set(nextDirection);
                        setSmoothPosYXByDirection(lastDirection.get());
                    }
                }
            }
        });
        smoothMoving.start();
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
                player.setFDMIndexY(player.getFDMIndexY() - 1);
                smoothMovePosY = speedPixelPerIterate;
            }
            case EAST -> {
                player.setFDMIndexX(player.getFDMIndexX() + 1);
                smoothMovePosX = -speedPixelPerIterate;
            }
            case SOUTH -> {
                player.setFDMIndexY(player.getFDMIndexY() + 1);
                smoothMovePosY = -speedPixelPerIterate;
            }
            case WEST -> {
                player.setFDMIndexX(player.getFDMIndexX() - 1);
                smoothMovePosX = speedPixelPerIterate;
            }
        }
        player.moveDirection(direction);
    }

    private BaseSQM insertFDMPositionToSQM(BaseSQM currentSQM, int playerXPosFDM, int playerYPosFDM) {
        BaseSQM sqm = FDMapToSQM.convertFDM_XY_ToSQM(playerXPosFDM, playerYPosFDM);
        currentSQM.loadNewImageIcon(sqm.getImageIcon());
        return currentSQM;
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
