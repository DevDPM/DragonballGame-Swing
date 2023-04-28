package nl.pokemon.game.config;

import nl.pokemon.game.domain.Session;
import nl.pokemon.game.core.model.tiles.VoidTile;
import nl.pokemon.game.client.model.FullTileMap;
import nl.pokemon.game.repository.ScoreRepository;
import nl.pokemon.game.client.view.DBCount;
import nl.pokemon.game.client.view.DBRadar;
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
