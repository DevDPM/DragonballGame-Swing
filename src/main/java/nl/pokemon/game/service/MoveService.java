package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.MapSQM;
import nl.pokemon.game.model.View.GridMap;
import nl.pokemon.game.model.View.ViewMap;
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
    ViewMapService viewMapService;

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
        playerService.moveDirection(direction);
        playerService.setCoordinationByDirection(direction);

        performMovement(direction);
    }

    private void performMovement(Direction direction) {

        AtomicReference<Direction> movedDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);
        Timer smoothMoving = new Timer(speedTimerDelay, actionEvent -> {
            viewMapService.getFullViewMap().keySet().forEach((z) -> {
                Map<AreaType, GridMap> viewSurfaceGridMap = viewMapService.getViewGridMapLayerLevel(z);

                Arrays.stream(AreaType.values()).forEach(area -> {
                    BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(area).getGridMap();

                    for (int y = 0; y < GridMap.MAX_Y; y++) {
                        for (int x = 0; x < GridMap.MAX_X; x++) {

                            BaseSQM viewSQM = viewGridMap[y][x];
                            int newPixelPosX = viewSQM.getPixelPosXByIndex() + smoothMovePosX;
                            int newPixelPosY = viewSQM.getPixelPosYByIndex() + smoothMovePosY;
                            viewSQM.moveSQM(newPixelPosX, newPixelPosY);
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
                viewMapService.updateView();
                performElevation((Elevatable) sqmService.getSQMByPlayerPosition());
                stopMovement(movedDirection, actionEvent);
                notMoving = false;
                elevating = true;
                playerService.setCoordinationByDirection(movedDirection.get());
                viewMapService.updateView();
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
                    performElevation((Elevatable) sqmService.getSQMByPlayerPosition());
                    stopMovement(movedDirection, actionEvent);
                    notMoving = false;
                    elevating = true;
                    move(movedDirection.get());
                }
                viewMapService.updateView();
                pixelCounter.set(0);
                movedDirection.set(nextDirection);
                setPixelMovement(movedDirection.get());
                playerService.moveDirection(nextDirection);
                playerService.setCoordinationByDirection(movedDirection.get());
            }
        }
    }

    private void performElevation(Elevatable elevatable) {
        playerService.addElevation(elevatable.getElevateZ());
    }

    private void stopMovement(AtomicReference<Direction> lastDirection, ActionEvent e) {
        Timer timer = (Timer) e.getSource();
        timer.stop();
        viewMapService.updateView();
        playerService.standStill(lastDirection.get());
        notMoving = true;
    }

    public boolean addOrReplaceFutureDirection(Direction direction) {
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
