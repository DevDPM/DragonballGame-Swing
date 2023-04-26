package nl.pokemon.game.view;

import nl.pokemon.game.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class TimeBox extends JLabel {

    private User user;

    public TimeBox(User user) {
        this.setVisible(true);
        this.setBounds(350, 0, 150,75);
        this.setFocusable(false);
        this.user = user;
        startTimer();
    }

    public void startTimer() {
        Timer timer = new Timer(1000, e -> {
            int startTime = user.getTime().toSecondOfDay();
            int currentTime = LocalTime.now().toSecondOfDay();

            int totalSeconds = currentTime - startTime;
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
