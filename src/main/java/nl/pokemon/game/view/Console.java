package nl.pokemon.game.view;

import nl.pokemon.game.controller.RpgController;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.service.ViewService;
import org.dpmFramework.Kickstarter;

import javax.swing.*;

public class Console extends JFrame {

    public Console() {

        this.setTitle("Pokemon!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.addKeyListener(Kickstarter.getInstanceOf(RpgController.class));
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(Kickstarter.getInstanceOf(CurrentPlayer.class));

        ViewService viewSQM = Kickstarter.getInstanceOf(ViewService.class);

        BaseSQM[][] fields = viewSQM.getViewMap();
        for (int y = 0; y < viewSQM.getMAX_Y(); y++) {
            for (int x = 0; x < viewSQM.getMAX_X(); x++) {
                this.add(fields[y][x]);
            }
        }
        viewSQM.updateView();

        this.setVisible(true);

    }
}
