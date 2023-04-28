package nl.pokemon.game.core.service;

import nl.pokemon.game.client.model.GameScreen;
import nl.pokemon.game.client.model.Movement;
import nl.pokemon.game.client.view.FoundDragonball;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.Tiles.ItemTile;
import nl.pokemon.game.core.model.Tiles.MapTile;
import nl.pokemon.game.core.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.domain.Session;
import nl.pokemon.game.core.model.players.BaseEntity;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Player {

    @Inject
    private Session session;

    @Inject
    private FullMapService fullMapService; // The full map with all details

    @Inject
    private GameScreen gameScreen; // The visible view screen

    @Inject
    private SQMService sqmService;

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
        AtomicInteger pixelCounter = new AtomicInteger(0);
        Timer smoothMoving = new Timer(SPEED_TIMER_DELAY, moveAction -> {
            if (pixelCounter.get() == 0) {
                if (moveStack.isEmpty()) {
                    stopMovingSequence(moveAction);
                    return;
                }
                direction.set(moveStack.pop());

                if (!sqmService.isWalkableTile(user, direction.get())) {
                    stopMovingSequence(moveAction);
                    return;
                }
                setWalkingImage(direction.get());
            }
            movement.moveScreenByDirection(direction.get());

            if (pixelCounter.getAndIncrement() >= (MapTile.SQM_PIXEL_WIDTH_X / Movement.SPEED_PIXEL_PER_ITERATE)) {
                updatePlayerCoordinates();
                checkTileForDragonball();
                pixelCounter.set(0);
            }
        });
        smoothMoving.start();
    }

    private void checkTileForDragonball() {
        if (sqmService.isDragonBallTile(user.getMapCoordination())) {
            ItemTile item = sqmService.getDragonBallTile(user.getMapCoordination());
            addPoint(item.receivePoints());
            dragonBallContainer.getNextDragonBall();
            foundDragonball.showImage();
        }
    }

    private void updatePlayerCoordinates() {
        MapCoordination oldPosition = MapCoordination.copyOf(user.getMapCoordination());
        MapCoordination nextPosition = MapCoordination.getPlayerPositionWithDirection(user, direction.get());

        if (sqmService.isElevatingSQM(nextPosition)) {
            Elevatable elevatingTile = (Elevatable) sqmService.getBaseTile(nextPosition, Elevatable.getAreaType());
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

    public void standStill(Direction direction) {
        user.getCharacter().setStandingImage(direction);
        gameScreen.updateView();
    }

    public MapCoordination getUserCoordination() {
        return user.getMapCoordination();
    }

    public User getUserInstance() {
        return user;
    }

    public BaseEntity getUserCharacter() {
        return user.getCharacter();
    }

    public void addPoint(int receivePoints) {
        user.addToPoints(receivePoints);
    }
}
