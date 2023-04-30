package nl.pokemon.game.config;

import nl.pokemon.game.model.client.FullTileMap;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

import javax.swing.*;

@Configurations
public class RpgConfig {

    @Enable(name = "rpgMessage")
    JLabel rpgMessage;

    @Enable
    FullTileMap fullMap;
}
