package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.service.ViewService;
import nl.pokemon.mapGenerator.controller.MG_Controller;
import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import nl.pokemon.mapGenerator.service.MG_ViewService;
import org.dpmFramework.Kickstarter;

import javax.swing.*;

public class MapGenerator extends JFrame {

    public MapGenerator() {

        this.setTitle("Pokemon Map Generator!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200, 1200);
        this.addKeyListener(Kickstarter.getInstanceOf(MG_Controller.class));
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        MG_ViewService viewSQM = Kickstarter.getInstanceOf(MG_ViewService.class);

        MG_BaseSQM[][] fields = viewSQM.getViewMap();
        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields.length; x++) {
                this.add(fields[y][x]);
            }
        }

        this.setVisible(true);

    }
}
