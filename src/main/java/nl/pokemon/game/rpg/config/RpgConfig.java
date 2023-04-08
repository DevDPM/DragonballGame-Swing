package nl.pokemon.game.rpg.config;

import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;
import nl.pokemon.game.rpg.model.CurrentPlayer;

import javax.swing.*;

@Configurations
public class RpgConfig {

    @Enable
    CurrentPlayer player;

    @Enable(name = "rpgMessage")
    JLabel rpgMessage;
}
