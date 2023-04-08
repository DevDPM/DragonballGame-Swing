package nl.pokemon.player;

import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;
import nl.pokemon.player.model.User;

@Configurations
public class PlayerConfig {

    @Enable
    User user;

}
