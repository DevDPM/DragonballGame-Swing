package nl.pokemon.game.model;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.players.Goku;
import nl.pokemon.game.repository.UserRepository;
import nl.pokemon.game.service.FullMapManager;
import org.dpmFramework.Kickstarter;

public class Session {

    private User user;

    public Session() {
        User user = new User("Daniel", new Goku(), new MapCoordination(45, 63, 0, AreaType.PLAYER_BOTTOM));

        this.user = user;
//        Kickstarter.getInstanceOf(UserRepository.class).getUserDataBase().put(user.getId(), user);
//        FullMapManager fullMapManager = Kickstarter.getInstanceOf(FullMapManager.class);
//
//        fullMapManager.moveUser(user, Direction.EAST);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
