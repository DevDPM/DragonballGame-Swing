package nl.pokemon.game.config;

import nl.pokemon.game.model.CurrentPlayer;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

import javax.swing.*;

@Configurations
public class RpgConfig {

    @Enable
    CurrentPlayer player;

    @Enable(name = "rpgMessage")
    JLabel rpgMessage;
}
