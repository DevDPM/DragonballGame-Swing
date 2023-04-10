package nl.pokemon.mapGenerator.view;

import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import nl.pokemon.mapGenerator.service.MG_ViewService;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class ViewPanel extends JPanel {

    public ViewPanel() {
        this.setLayout(null);
    }

    public void init() {
        MG_ViewService viewSQM = Kickstarter.getInstanceOf(MG_ViewService.class);
        MG_BaseSQM[][] fields = viewSQM.getViewMap();
        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields.length; x++) {
                this.add(fields[y][x]);
            }
        }
    }
}
