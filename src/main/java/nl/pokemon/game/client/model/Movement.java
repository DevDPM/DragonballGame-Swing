package nl.pokemon.game.client.model;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.MapTile;
import nl.pokemon.game.core.service.FullMapManager;
import nl.pokemon.game.core.service.SQMService;
import nl.pokemon.game.core.service.UserService;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Movement {

    private final int speedPixelPerIterate = 10; // 50 = max
    private final int speedTimerDelay = 10;

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    UserService userService;

    @Inject
    GameScreen gameScreen;

    @Inject
    FullMapManager fullMapManager;

    @Inject
    SQMService sqmService;

    Stack<Direction> moveStack = new Stack<>();
    boolean notMoving = true;


    public void moveScreenByDirection(Direction direction) {

        User user = userService.getUserInstance();

        if (!sqmService.isWalkableSQM(user, direction)) {
            userService.standStill(direction);
            gameScreen.updateView();
            notMoving = true;
            return;
        }

        userService.setWalkingImage(direction);
        gameScreen.adjustPlayerToTopLayerByElevation(direction, user);
        gameScreen.updateView();
        setPixelMovement(direction);

        iterateScreenMovement(direction, user);
    }

    private void iterateScreenMovement(Direction direction, User user) {
        AtomicReference<Direction> movedDirection = new AtomicReference<>(direction);
        AtomicInteger pixelCounter = new AtomicInteger(0);

        Timer smoothMoving = new Timer(speedTimerDelay, actionEvent -> {
            gameScreen.getFullViewMap().keySet().forEach((z) -> {
                Map<AreaType, TileMap> viewSurfaceGridMap = gameScreen.getViewGridMapLayerLevel(z);

                for (AreaType areaType : AreaType.values()) {
                    BaseTile[][] viewGridMap = viewSurfaceGridMap.get(areaType).getTileMap();

                    for (int y = 0; y < TileMap.MAX_Y; y++) {
                        for (int x = 0; x < TileMap.MAX_X; x++) {

                            BaseTile viewSQM = viewGridMap[y][x];
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

            if (pixelCounter.getAndIncrement() >= (MapTile.SQM_PIXEL_WIDTH_X/speedPixelPerIterate)) {
                finalizeMovement(user, movedDirection, pixelCounter, actionEvent);
            }
        });
        smoothMoving.start();
    }

    private void finalizeMovement(User user, AtomicReference<Direction> movedDirection, AtomicInteger pixelCounter, ActionEvent actionEvent) {
        fullMapManager.moveUser(user, movedDirection.get());

        if (moveStack.isEmpty()) {
            stopMovement(movedDirection, actionEvent);
        } else {
            Direction nextDirection = moveStack.pop();

            if (!sqmService.isWalkableSQM(user, nextDirection)) {
                stopMovement(movedDirection, actionEvent);
            } else {
                gameScreen.adjustPlayerToTopLayerByElevation(nextDirection, user);
                userService.setWalkingImage(nextDirection);
                gameScreen.updateView();
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
        gameScreen.updateView();
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
            case NORTH -> smoothMovePosY = speedPixelPerIterate;
            case EAST -> smoothMovePosX = -speedPixelPerIterate;
            case SOUTH -> smoothMovePosY = -speedPixelPerIterate;
            case WEST -> smoothMovePosX = speedPixelPerIterate;
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
