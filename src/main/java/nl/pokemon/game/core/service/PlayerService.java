package nl.pokemon.game.core.service;

import nl.pokemon.game.client.model.GameScreen;
import nl.pokemon.game.client.model.Movement;
import nl.pokemon.game.client.view.DBCount;
import nl.pokemon.game.client.view.FoundDragonball;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.tiles.ItemTile;
import nl.pokemon.game.core.model.tiles.MapTile;
import nl.pokemon.game.core.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.domain.Session;
import nl.pokemon.game.core.model.characters.BaseEntity;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PlayerService {

    @Inject
    private Session session;

    @Inject
    private FullMapService fullMapService; // The full map with all details

    @Inject
    private DBCount dbCount;

    @Inject
    private GameScreen gameScreen; // The visible view screen

    @Inject
    private TileService tileService;

    @Inject
    private Movement movement;

    @Inject
    private DragonBallContainer dragonBallContainer;

    @Inject
    private FoundDragonball foundDragonball;

    public static final int SPEED_TIMER_DELAY = 10;
    private User user;
    Stack<Direction> moveStack = new Stack<>();
    AtomicReference<Direction> direction = new AtomicReference<>();

    boolean characterMoving = false;

    private void init() {
        this.user = session.getUser();
    }

    public void startMovingSequence() {
        characterMoving = true;
        AtomicInteger iterateCount = new AtomicInteger(0);
        Timer smoothMoving = new Timer(SPEED_TIMER_DELAY, moveAction -> {
            if (iterateCount.get() == 0) {
                if (moveStack.isEmpty()) {
                    stopMovingSequence(moveAction);
                    return;
                }
                direction.set(moveStack.pop());
                if (!tileService.isWalkableTile(user, direction.get())) {
                    stopMovingSequence(moveAction);
                    return;
                }
                if (tileService.isElevatingSQM(MapCoordination.getPlayerPositionWithDirection(user, direction.get())))
                    fullMapService.moveToTopLayer(user);
                setWalkingImage(direction.get());
            }
            movement.moveScreenByDirection(direction.get());
            if (iterateCount.getAndIncrement() >= (MapTile.SQM_PIXEL_WIDTH_X / Movement.SPEED_PIXEL_PER_ITERATE)) {
                updatePlayerCoordinates();
                checkTileForDragonball();
                iterateCount.set(0);
            }
        });
        smoothMoving.start();
    }

    private void checkTileForDragonball() {
        if (tileService.isDragonBallTile(user.getMapCoordination())) {
            ItemTile item = tileService.getDragonBallTile(user.getMapCoordination());
            addPoint(item.receivePoints());
            dbCount.addDragonBall(item);
            dragonBallContainer.releaseNextDragonBall();
            foundDragonball.showImage();
        }
    }

    private void updatePlayerCoordinates() {
        MapCoordination oldPosition = MapCoordination.copyOf(user.getMapCoordination());
        MapCoordination nextPosition = MapCoordination.getPlayerPositionWithDirection(user, direction.get());

        if (tileService.isElevatingSQM(nextPosition)) {
            Elevatable elevatingTile = (Elevatable) tileService.getBaseTile(nextPosition, Elevatable.getAreaType());
            user.getMapCoordination().elevate(elevatingTile.elevatingValue(), direction.get());
        } else {
            user.getMapCoordination().incrementByDirection(direction.get());
        }
        fullMapService.updateUserPosition(oldPosition, user);
    }

    private void stopMovingSequence(ActionEvent moveAction) {
        Timer timer = (Timer) moveAction.getSource();
        timer.stop();
        standStill(direction.get());
        characterMoving = false;
    }

    public boolean addOrUpdateDirection(Direction direction) {
        if (!moveStack.isEmpty()) {
            moveStack.pop();
        }
        return moveStack.add(direction);
    }

    public boolean isCharacterMoving() {
        return characterMoving;
    }

    public void setWalkingImage(Direction direction) {
        user.getCharacter().setWalkingImage(direction);
        gameScreen.updateView();
    }

    public int getPoints() {
        return user.getPoints();
    }

    public void standStill(Direction direction) {
        user.getCharacter().setStandingImage(direction);
        gameScreen.updateView();
    }

    public MapCoordination getUserCoordination() {
        return user.getMapCoordination();
    }

    public BaseEntity getUserCharacter() {
        return user.getCharacter();
    }

    public void addPoint(int receivePoints) {
        user.addToPoints(receivePoints);
    }
}
