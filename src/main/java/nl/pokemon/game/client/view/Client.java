package nl.pokemon.game.client.view;

import nl.pokemon.game.client.controller.GameController;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.core.model.tiles.BaseTile;
import nl.pokemon.game.client.model.TileMap;
import nl.pokemon.game.client.model.FullTileMap;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Client extends JFrame {

    public Client() {

        this.setTitle("Dragon ball Z!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(Kickstarter.getInstanceOf(MenuPanel.class));


        this.setVisible(true);
    }
}
