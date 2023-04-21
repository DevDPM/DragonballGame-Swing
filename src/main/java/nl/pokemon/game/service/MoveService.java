package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.MapSQM;
import nl.pokemon.game.model.View.GridMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoveService {

    private final int speedPixelPerIterate = 5; // 50 = max
    private final int speedTimerDelay = 20;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    SQMService sqmService;

    @Inject
    PlayerService playerService;

    @Inject
    ClientViewMap clientViewMap;

    @Inject
    FullMapService fullMapService;

    Stack<Direction> moveStack = new Stack<>();
    boolean notMoving = true;
    boolean elevating = false;


    public void move(Direction direction) {

        BaseSQM sqm = sqmService.getBaseSQMByDirection(direction);
        if (sqm == null || sqm.isNotWalkable()) {
            notMoving = true;
            return;
        }


        setPixelMovement(direction);
        playerService.setWalkingImage(direction);
//        viewMapService.updateView();

        fullMapService.moveUserByDirection(playerService.getPlayerById(1), direction);
        playerService.moveEntity(direction);

        performMovement(direction);
    }

    private void performMovement(Direction direction) {

        AtomicReference<Direction> movedDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);
        Timer smoothMoving = new Timer(speedTimerDelay, actionEvent -> {
            clientViewMap.getFullViewMap().keySet().forEach((z) -> {
                Map<AreaType, GridMap> viewSurfaceGridMap = clientViewMap.getViewGridMapLayerLevel(z);

                Arrays.stream(AreaType.values()).forEach(area -> {
                    BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(area).getGridMap();

                    for (int y = 0; y < GridMap.MAX_Y; y++) {
                        for (int x = 0; x < GridMap.MAX_X; x++) {

                            BaseSQM viewSQM = viewGridMap[y][x];

                            if (!((x == 10 && y == 10 && z == playerService.getPlayerZ()) &&
                                    area.equals(playerService.getPlayerArea()))) {
                                int newPixelPosX;
                                int newPixelPosY;
                                newPixelPosX = viewSQM.getPixelPosXByIndex() + smoothMovePosX;
                                newPixelPosY = viewSQM.getPixelPosYByIndex() + smoothMovePosY;
                                viewSQM.moveSQM(newPixelPosX, newPixelPosY);
                            }
                        }
                    }
                });
            });

            if (pixelCounter.getAndIncrement() >= (MapSQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {
                finalizeMovement(movedDirection, pixelCounter, actionEvent);
            }
        });
        smoothMoving.start();
    }

    private void finalizeMovement(AtomicReference<Direction> movedDirection, AtomicInteger pixelCounter, ActionEvent actionEvent) {
        if (moveStack.isEmpty()) {
            if (sqmService.isElevatable(sqmService.getSQMByPlayerPosition())) {
                Elevatable elevatable = (Elevatable) sqmService.getSQMByPlayerPosition();
                playerService.moveEntity(movedDirection.get(), elevatable.getElevateZ());
                clientViewMap.updateView();
                stopMovement(movedDirection, actionEvent);
                notMoving = false;
                elevating = true;
                clientViewMap.updateView();
                elevating = false;
                stopMovement(movedDirection, actionEvent);
            } else {
                stopMovement(movedDirection, actionEvent);
            }
        } else {
            Direction nextDirection = moveStack.pop();
            if (sqmService.isNotWalkable(nextDirection)) {
                stopMovement(movedDirection, actionEvent);
            } else {
                if (sqmService.isElevatable(sqmService.getSQMByPlayerPosition())) {
                    Elevatable elevatable = (Elevatable) sqmService.getSQMByPlayerPosition();
                    playerService.moveEntity(movedDirection.get(), elevatable.getElevateZ());
                    stopMovement(movedDirection, actionEvent);
                    clientViewMap.updateView();
                    notMoving = false;
                    elevating = true;
                    move(movedDirection.get());
                }
                clientViewMap.updateView();
                pixelCounter.set(0);
                movedDirection.set(nextDirection);
                setPixelMovement(movedDirection.get());

                playerService.setWalkingImage(nextDirection);
                clientViewMap.updateView();
                fullMapService.moveUserByDirection(playerService.getPlayerById(1), nextDirection);
                playerService.moveEntity(nextDirection);
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

    public boolean isElevating() {
        return elevating;
    }

    public void setElevating(boolean elevating) {
        this.elevating = elevating;
    }
}
