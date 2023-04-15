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
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoveService {

    private final int speedPixelPerIterate = 2; // 50 = max
    private final int speedTimerDelay = 5;

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
    boolean delayedStairWalking = false;


    public void move(Direction direction) {

        BaseSQM sqm = sqmService.getBaseSQMByDirection(direction);
        if (sqm == null || sqm.isNotWalkable()) {
            notMoving = true;
            return;
        }

        setPixelMovement(direction);
        playerService.moveDirection(direction);
        playerService.setCoordinationByDirection(direction);

//        int speedDelay = speedTimerDelay;
//
//        if (delayedStairWalking) {
//            delayedStairWalking = false;
//            speedDelay = speedDelay * 100;
//        }

        performMovement(direction);
    }

    private void performMovement(Direction direction) {

        AtomicReference<Direction> movedDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);
        Timer smoothMoving = new Timer(speedTimerDelay, actionEvent -> {
            for (int z = ViewMap.START_Z; z <= ViewMap.MAX_Z; z++) {
                Map<AreaType, GridMap> viewSurfaceGridMap = viewMapService.getViewGridMapLayerLevel(z);

                for (AreaType area : AreaType.values()) {
                    BaseSQM[][] viewGridMap = viewSurfaceGridMap.get(area).getGridMap();

                    for (int y = 0; y < GridMap.MAX_Y; y++) {
                        for (int x = 0; x < GridMap.MAX_X; x++) {

                            BaseSQM viewSQM = viewGridMap[y][x];
                            int newPixelPosX = viewSQM.getPixelPosXByIndex() + smoothMovePosX;
                            int newPixelPosY = viewSQM.getPixelPosYByIndex() + smoothMovePosY;
                            viewSQM.moveSQM(newPixelPosX, newPixelPosY);
                        }
                    }
                }
            }

            if (pixelCounter.getAndIncrement() >= (MapSQM.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {
                finalizeMovement(movedDirection, pixelCounter, actionEvent);
            }
        });
        smoothMoving.start();
    }

    private void finalizeMovement(AtomicReference<Direction> movedDirection, AtomicInteger pixelCounter, ActionEvent actionEvent) {
        if (moveStack.isEmpty()) {
            if (sqmService.isElevatable(sqmService.getSQMByPlayerPosition())) {
                performElevation((Elevatable) sqmService.getSQMByPlayerPosition());
                stopMovement(movedDirection, actionEvent);
                notMoving = false;
                move(movedDirection.get());
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
        setNotMoving(true);
        viewMapService.updateView();
        playerService.standStill(lastDirection.get());
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
}
