package nl.pokemon.game.core.service;

import nl.pokemon.game.client.model.GameScreen;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.domain.Session;
import nl.pokemon.game.core.model.players.BaseEntity;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

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

    private void init() {
        this.user = session.getUser();
    }

    public void moveDirection(Direction direction) {
        if (!sqmService.isWalkableSQM(user, direction))
            return;

        Elevatable elevatingTile = sqmService.isElevatingSQMOrNull(user, direction);
        if (elevatingTile != null) {
            // service user make elevation
        } else {
            BaseTile baseTile = sqmService.isWalkableTerrainOrNull(user, direction);
            if (baseTile != null) {
                // make player elevated
            }
        }
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
