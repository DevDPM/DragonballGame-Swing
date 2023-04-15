package nl.pokemon.game.config;

import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.SQMs.VoidSQM;
import nl.pokemon.game.model.View.ViewMap;
import org.dpmFramework.annotation.Configurations;
import org.dpmFramework.annotation.Enable;

import javax.swing.*;

@Configurations
public class RpgConfig {

    @Enable
    CurrentPlayer player;

    @Enable(name = "rpgMessage")
    JLabel rpgMessage;

    @Enable
    ViewMap fullMap;

    @Enable
    VoidSQM voidSQM;
}
