package nl.pokemon.game.client.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class DBRadar extends JLabel {

    private boolean visibility = false;
    private int timeLastUse;

    public DBRadar() {
        this.setFocusable(false);
        this.setBounds(275, 100, 300, 50);
        this.setVisible(true);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Monaco", Font.BOLD, 15));

    }

    public void useDragonBallRadar() {
        if (!visibility) {
            visibility = true;
            this.setVisible(true);

            int timeNow = LocalTime.now().toSecondOfDay();
            int delayTime = timeNow - timeLastUse;

            if (delayTime >= 20) {
                // make calculations and return string " far/[nothing]/close i.e. north-west
                this.setText("Dragonball detected far north-west");
                timeLastUse = LocalTime.now().toSecondOfDay();
            } else {
                this.setText("Dragon radar is still re-charging..");
            }

            Timer visibilityTime = new Timer(5000, e -> {
                this.setVisible(false);
                this.visibility = false;
                Timer thisTimer = (Timer) e.getSource();
                thisTimer.stop();
            });
            visibilityTime.start();
        }
    }

    private String getHintsByRadar() {
        return null;
    }


}
