package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.players.BaseEntity;
import nl.pokemon.game.repository.UserRepository;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class PlayerService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private FullMapManager fullMapManager; // The full map with all details

    @Inject
    private ClientViewMap clientViewMap; // The visible view screen

    public void moveEntity(Direction direction) {
        moveEntity(direction, 0);
    }

    public void moveEntity(Direction direction, int z) {
        User user = userRepository.getUserDataBase().get(1);

        if (z != 0) {
            performElevation(z, user);
        }

        switch (direction) {
            case NORTH -> user.setY(user.getY() - 1);
            case EAST -> user.setX(user.getX() + 1);
            case SOUTH -> user.setY(user.getY() + 1);
            case WEST -> user.setX(user.getX() - 1);
        }

        // performDirection
    }

    private void performElevation(int z, User user) {
        int oldZ = user.getZ();
        int newZ = oldZ + z;

        user.setZ(newZ);

//        fullMapService.setElevationZ(user.getAreaType(), user.getX(), user.getY(), oldZ, 0);
//        fullMapService.setElevationZ(user.getAreaType(), user.getX(), user.getY(), oldZ, user.getId());
//
//        clientViewMap.changeSQMVisibility(user.getAreaType(), user.getX(), user.getY(), oldZ);
//        clientViewMap.changeSQMVisibility(user.getAreaType(), user.getX(), user.getY(), user.getZ());
    }

    // moveDirection by

    public void setWalkingImage(Direction direction) {
        User user = userRepository.getUserDataBase().get(1);
        user.getBaseEntity().setWalkingImage(direction);
//        clientViewMap.updateSQM(user.getAreaType(), user.getX(), user.getY(), user.getZ());
    }

    public void standStill(Direction direction) {
        User user = userRepository.getUserDataBase().get(1);
        user.getBaseEntity().setStandingImage(direction);
    }

    public int getPlayerX() {
        User user = userRepository.getUserDataBase().get(1);
        return user.getX();
    }

    public int getPlayerY() {
        User user = userRepository.getUserDataBase().get(1);
        return user.getY();
    }

    public int getPlayerZ() {
        User user = userRepository.getUserDataBase().get(1);
        return user.getZ();
    }

    public User getPlayerById(int id) {
        return userRepository.getUserDataBase().get(id);
    }

    public BaseEntity getPlayerSQMByUserId(int id) {
        return userRepository.getUserDataBase().get(id).getBaseEntity();
    }

    public AreaType getPlayerArea() {
        User user = userRepository.getUserDataBase().get(1);
        return user.getAreaType();
    }

    public AreaType setPlayerArea(AreaType areaType) {
        User user = userRepository.getUserDataBase().get(1);
        user.setAreaType(areaType);
        return user.getAreaType();
    }

    public void setNewPosition(User user, int x, int y, int z, AreaType areaType) {
        user.setX(x);
        user.setY(y);
        user.setZ(z);
        user.setAreaType(areaType);
    }
}
