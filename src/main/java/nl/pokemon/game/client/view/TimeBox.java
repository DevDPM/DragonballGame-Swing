package nl.pokemon.game.client.view;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.core.model.dragonballs.DragonBallContainer;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class TimeBox extends JLabel {

    public TimeBox() {
        this.setVisible(true);
        this.setBounds(350, 0, 150,75);
        this.setFocusable(false);
        startTimer();
    }

    public void startTimer() {
        int startTime = LocalTime.now().toSecondOfDay();
        Timer timer = new Timer(1000, e -> {

            int currentTime = LocalTime.now().toSecondOfDay();

            int totalSeconds = currentTime - startTime;
            if (totalSeconds == 5) {
                Kickstarter.getInstanceOf(DragonBallContainer.class).getNextDragonBall();
            }
            if (totalSeconds < 60) {
                this.setText("Time: " + totalSeconds + "s");
            } else {
                int totalMinutes = totalSeconds / 60;
                int remainderSeconds = totalSeconds % 60;
                this.setBounds(350, 0, 250,75);
                this.setText("Time: " + totalMinutes + "m " + remainderSeconds + "s");
            }
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Monaco", Font.BOLD, 30));
        });
        timer.start();
    }

}
