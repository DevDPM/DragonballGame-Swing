package nl.pokemon.game.config;

import nl.pokemon.game.model.Session;
import nl.pokemon.game.model.SQMs.VoidSQM;
import nl.pokemon.game.model.clientViewMap.ViewMap;
import nl.pokemon.game.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.repository.UserRepository;
import nl.pokemon.game.view.DragonBallCounter;
import nl.pokemon.game.view.DragonBallRadar;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

import javax.swing.*;

@Configurations
public class RpgConfig {

    @Enable(name = "rpgMessage")
    JLabel rpgMessage;

    @Enable
    ViewMap fullMap;

    @Enable
    VoidSQM voidSQM;

    @Enable
    UserRepository userRepository;

    @Enable
    DragonBallCounter dragonBallCounter;

    @Enable
    DragonBallRadar dragonBallRadar;

    @Enable
    Session session;
}
