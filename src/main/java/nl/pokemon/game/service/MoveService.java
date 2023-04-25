package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.MapSQM;
import nl.pokemon.game.model.View.GridMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoveService {

    private final int speedPixelPerIterate = 5; // 50 = max
    private final int speedTimerDelay = 10;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    PlayerService playerService;

    @Inject
    ClientViewMap clientViewMap;

    @Inject
    FullMapManager fullMapManager;

    Stack<Direction> moveStack = new Stack<>();
    boolean notMoving = true;


    public void move(Direction direction) {

        User player = playerService.getPlayerById(1);

        if (!fullMapManager.isWalkableSQM(player, direction)) {
            playerService.standStill(direction);
            clientViewMap.updateView();
            notMoving = true;
            return;
        }

        playerService.setWalkingImage(direction);
        clientViewMap.adjustPlayerToTopLayerByElevation(direction, player);
        clientViewMap.adjustPlayerToTopLayerByTerrain(direction, player);
        clientViewMap.updateView();
        setPixelMovement(direction);

        performMovement(direction);
    }

    private void performMovement(Direction direction) {

        AtomicReference<Direction> movedDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);

        Timer smoothMoving = new Timer(speedTimerDelay, actionEvent -> {
            clientViewMap.getFullViewMap().keySet().forEach((z) -> {
                Map<AreaType, GridMap> viewSurfaceGridMap = clientViewMap.getViewGridMapLayerLevel(z);

                for (AreaType areaType : AreaType.values()) {
                    BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(areaType).getGridMap();

                    for (int y = 0; y < GridMap.MAX_Y; y++) {
                        for (int x = 0; x < GridMap.MAX_X; x++) {

                            BaseSQM viewSQM = viewGridMap[y][x];

                            if (!((x == 10 && y == 10 && z == playerService.getPlayerZ()) &&
                                    areaType.equals(playerService.getPlayerArea()))) {
                                int newPixelPosX;
                                int newPixelPosY;
                                newPixelPosX = viewSQM.getPixelPosXByIndex() + smoothMovePosX;
                                newPixelPosY = viewSQM.getPixelPosYByIndex() + smoothMovePosY;
                                viewSQM.moveSQM(newPixelPosX, newPixelPosY);
                            }
                        }
                    }
                }
            });

            if (pixelCounter.getAndIncrement() >= (MapSQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {
                finalizeMovement(movedDirection, pixelCounter, actionEvent);
            }
        });
        smoothMoving.start();
    }

    private void finalizeMovement(AtomicReference<Direction> movedDirection, AtomicInteger pixelCounter, ActionEvent actionEvent) {
        fullMapManager.moveUserByDirection(playerService.getPlayerById(1), movedDirection.get());

        if (moveStack.isEmpty()) {
            clientViewMap.adjustPlayerToTopLayerByElevation(movedDirection.get(), playerService.getPlayerById(1));
            clientViewMap.adjustPlayerToTopLayerByTerrain(movedDirection.get(), playerService.getPlayerById(1));
                stopMovement(movedDirection, actionEvent);
        } else {
            Direction nextDirection = moveStack.pop();

            if (!fullMapManager.isWalkableSQM(playerService.getPlayerById(1), nextDirection)) {
                stopMovement(movedDirection, actionEvent);
            } else {
                clientViewMap.adjustPlayerToTopLayerByElevation(nextDirection, playerService.getPlayerById(1));
                clientViewMap.adjustPlayerToTopLayerByTerrain(nextDirection, playerService.getPlayerById(1));
                playerService.setWalkingImage(nextDirection);
                clientViewMap.updateView();
                pixelCounter.set(0);
                movedDirection.set(nextDirection);
                setPixelMovement(movedDirection.get());

            }
        }
    }

    private void stopMovement(AtomicReference<Direction> lastDirection, ActionEvent e) {
        Timer timer = (Timer) e.getSource();
        timer.stop();
        playerService.standStill(lastDirection.get());
        clientViewMap.updateView();
        notMoving = true;
    }

    public boolean addOrUpdateDirection(Direction direction) {
        if (!moveStack.isEmpty()) {
            moveStack.pop();
        }
        return moveStack.add(direction);
    }

    private void setPixelMovement(Direction direction) {
        this.smoothMovePosX = 0;
        this.smoothMovePosY = 0;

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
    }

    public Stack<Direction> getMoveStack() {
        return moveStack;
    }

    public boolean isNotMoving() {
        return notMoving;
    }

    public void setNotMoving(boolean notMoving) {
        this.notMoving = notMoving;
    }
}
