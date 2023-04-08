package nl.pokemon.game.rpg.view;

import nl.pokemon.game.rpg.controller.RpgController;
import nl.pokemon.game.rpg.model.BaseSQM;
import nl.pokemon.game.rpg.model.CurrentPlayer;
import nl.pokemon.game.rpg.service.ViewService;
import org.dpmFramework.Kickstarter;

import javax.swing.*;

public class Rpg extends JFrame {

    public Rpg() {

        this.setTitle("RPG!     ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.addKeyListener(Kickstarter.getInstanceOf(RpgController.class));
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(Kickstarter.getInstanceOf(CurrentPlayer.class));


//        Kickstarter.getInstanceOf(MapServiceImpl.class).generateMap();

        ViewService viewSQM = Kickstarter.getInstanceOf(ViewService.class);

        BaseSQM[][] fields = viewSQM.getViewMap();
        for (int y = 0; y < viewSQM.getMAX_Y(); y++) {
            for (int x = 0; x < viewSQM.getMAX_X(); x++) {
                if (fields[y][x] == null)
                    return;
                this.add(fields[y][x]);
            }
        }
        viewSQM.updateView();

        this.setVisible(true);

    }
}
