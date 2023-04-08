package nl.pokemon.game.rpg.service;

import nl.pokemon.game.rpg.controller.RpgController;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;
import nl.pokemon.game.rpg.model.BaseSQM;
import nl.pokemon.game.rpg.model.CurrentPlayer;
import nl.pokemon.game.rpg.model.SQM;
import nl.pokemon.game.rpg.model.obstacle.GreenPlant;
import nl.pokemon.game.rpg.model.obstacle.bigTree.BigTreeLL;
import nl.pokemon.game.rpg.model.obstacle.bigTree.BigTreeLR;
import nl.pokemon.game.rpg.model.obstacle.bigTree.BigTreeTL;
import nl.pokemon.game.rpg.model.obstacle.bigTree.BigTreeTR;
import nl.pokemon.game.rpg.model.walk.Grass;
import nl.pokemon.game.rpg.model.walk.Gravel;
import nl.pokemon.game.rpg.util.FullDataMap;

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
    RpgController controller;

    @Inject
    CurrentPlayer player;

    private void init() {
        FullDataMap.generate();
        viewMap = new BaseSQM[MAX_Y][MAX_X];

        StringBuilder sb = new StringBuilder();

        for (int viewY = 0; viewY < MAX_Y; viewY++) {
            for (int viewX = 0; viewX < MAX_X; viewX++) {
                sb.append(viewY+1).append("-").append(viewX+1);
                BaseSQM sqm = Kickstarter.getInstanceOf(SQM.class, sb.toString());
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
                viewMap[y][x] = sqm;
                sqm.updateBounds();

            }
        }
    }

    public void moveViewXYSmoothly(Direction direction) {

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

            if (pixelCounter.getAndIncrement() >= (SQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {

                if (controller.getMoveStack().isEmpty()) {
                    Timer timer = (Timer) e.getSource();
                    timer.stop();
                    controller.setNotMoving(true);
                    setViewToDefaultXY();
                    updateView();
                    player.standStill(lastDirection.get());
                } else {
                    pixelCounter.set(0);
                    setViewToDefaultXY();
                    updateView();
                    lastDirection.set(controller.getMoveStack().pop());
                    setSmoothPosYXByDirection(lastDirection.get());
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
        int[][] dataMap = FullDataMap.generate();
        BaseSQM sqm = switch(dataMap[playerYPosFDM][playerXPosFDM]) {
            case 0 -> new Gravel();
            case 1 -> new Grass();
            case 3 -> new GreenPlant();
            case 91 -> new BigTreeLL();
            case 92 -> new BigTreeLR();
            case 93 -> new BigTreeTL();
            case 94 -> new BigTreeTR();
            default -> new SQM();
        };

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

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
}
