package nl.pokemon.game.core.service;

import nl.pokemon.game.client.model.GameScreen;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.ItemTile;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.domain.Session;
import nl.pokemon.game.core.model.players.BaseEntity;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.Stack;

@Service
public class UserService {

    @Inject
    Session session;

    @Inject
    private FullMapManager fullMapManager; // The full map with all details

    @Inject
    private GameScreen gameScreen; // The visible view screen

    @Inject
    SQMService sqmService;

    private User user;

    Stack<Direction> moveStack = new Stack<>();

    private void init() {
        this.user = session.getUser();
    }

    public void moveDirection(Direction direction) {
        if (!sqmService.isWalkableSQM(user, direction))
            return;


        if (true) {
            // perform elevation
        } else {
            // perform movement
            // change fullmap data
            // update screen
        }


    }

    public boolean addOrUpdateDirection(Direction direction) {
        if (!moveStack.isEmpty()) {
            moveStack.pop();
        }
        return moveStack.add(direction);
    }













    private MapCoordination getMapCoordinationByDirection(Direction direction) {
        MapCoordination mapCoordination = user.getMapCoordination();
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();

        if (direction != null) {
            x += direction.getX();
            y += direction.getY();
        }
        return new MapCoordination(x, y, z, mapCoordination.getAreaType());
    }

    public void setWalkingImage(Direction direction) {
        user.getCharacter().setWalkingImage(direction);
    }

    public void standStill(Direction direction) {
        user.getCharacter().setStandingImage(direction);
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
