package nl.pokemon.game.repository;

import nl.pokemon.game.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private Map<Integer, User> userDataBase = new HashMap<>();

    public Map<Integer, User> getUserDataBase() {
        return userDataBase;
    }

    public void setUserDataBase(Map<Integer, User> userDataBase) {
        this.userDataBase = userDataBase;
    }
}
