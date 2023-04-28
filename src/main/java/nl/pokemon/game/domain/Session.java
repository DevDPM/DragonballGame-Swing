package nl.pokemon.game.domain;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.characters.Goku;
import nl.pokemon.game.core.service.FullMapService;
import org.dpmFramework.Kickstarter;

public class Session {

    private User user;

    public Session() {
        User user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void start() {
        Kickstarter.getInstanceOf(FullMapService.class).setUserPosition(this.user);
    }
}
