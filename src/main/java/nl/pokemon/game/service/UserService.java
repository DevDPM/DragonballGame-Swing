package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.Session;
import nl.pokemon.game.model.players.BaseEntity;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class UserService {

    @Inject
    Session session;

    @Inject
    private FullMapManager fullMapManager; // The full map with all details

    @Inject
    private ClientViewMap clientViewMap; // The visible view screen
    private User user;

    private void init() {
        this.user = session.getUser();
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
