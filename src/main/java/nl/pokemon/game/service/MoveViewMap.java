package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.MapSQM;
import nl.pokemon.game.model.clientViewMap.GridMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoveViewMap {

    private final int speedPixelPerIterate = 10; // 50 = max
    private final int speedTimerDelay = 10;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    UserService userService;

    @Inject
    ClientViewMap clientViewMap;

    @Inject
    FullMapManager fullMapManager;

    @Inject
    SQMService sqmService;

    Stack<Direction> moveStack = new Stack<>();
    boolean notMoving = true;


    public void move(Direction direction) {

        User user = userService.getUserInstance();

        if (!sqmService.isWalkableSQM(user, direction)) {
            userService.standStill(direction);
            clientViewMap.updateView();
            notMoving = true;
            return;
        }

        userService.setWalkingImage(direction);
        clientViewMap.adjustPlayerToTopLayerByElevation(direction, user);
        clientViewMap.adjustPlayerToTopLayerByTerrain(direction, user);
        clientViewMap.updateView();
        setPixelMovement(direction);

        iterateScreenMovement(direction, user);
    }

    private void iterateScreenMovement(Direction direction, User user) {
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
                            MapCoordination userCoordinate = user.getMapCoordination();
                            if (!((x == 10 && y == 10 && z == userCoordinate.getZ()) &&
                                    areaType.equals(userCoordinate.getAreaType()))) {
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
                finalizeMovement(user, movedDirection, pixelCounter, actionEvent);
            }
        });
        smoothMoving.start();
    }

    private void finalizeMovement(User user, AtomicReference<Direction> movedDirection, AtomicInteger pixelCounter, ActionEvent actionEvent) {
        fullMapManager.moveUser(user, movedDirection.get());

        if (moveStack.isEmpty()) {
            clientViewMap.adjustPlayerToTopLayerByTerrain(user);
            stopMovement(movedDirection, actionEvent);
        } else {
            Direction nextDirection = moveStack.pop();

            if (!sqmService.isWalkableSQM(user, nextDirection)) {
                stopMovement(movedDirection, actionEvent);
            } else {
                clientViewMap.adjustPlayerToTopLayerByElevation(nextDirection, user);
                clientViewMap.adjustPlayerToTopLayerByTerrain(nextDirection, user);
                userService.setWalkingImage(nextDirection);
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
        userService.standStill(lastDirection.get());
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
