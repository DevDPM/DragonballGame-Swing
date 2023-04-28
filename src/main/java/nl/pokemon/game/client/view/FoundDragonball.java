package nl.pokemon.game.client.view;

import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class FoundDragonball extends JLabel {

    public FoundDragonball() {
        this.setVisible(false);
        this.setFocusable(false);
        this.setBounds(400, 235, 100, 100);
        this.setIcon(new ImageIcon("src/main/resources/images/console/explanation-mark.png"));
    }

    public void showImage() {
        this.setVisible(true);
        Timer timer = new Timer(4000, e -> {
            this.setVisible(false);
            Timer thisTimer = (Timer) e.getSource();
            thisTimer.stop();
        });
        timer.start();
    }
}
