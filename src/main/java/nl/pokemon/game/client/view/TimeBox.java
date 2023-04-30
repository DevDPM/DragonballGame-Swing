package nl.pokemon.game.client.view;

import nl.pokemon.game.core.model.dragonballs.DragonBallContainer;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

@Service
public class TimeBox extends JLabel {

    private Timer timer;
    private long playTimeInSeconds;

    public TimeBox() {
        this.setVisible(true);
        this.setBounds(350, 0, 150,75);
        this.setFocusable(false);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Monaco", Font.BOLD, 30));
    }

    public void startTimer() {
        if (timer != null && timer.isRunning())
            return;

        int startTime = LocalTime.now().toSecondOfDay();
         timer = new Timer(1000, e -> {
            int currentTime = LocalTime.now().toSecondOfDay();

             playTimeInSeconds = currentTime - startTime;
            if (playTimeInSeconds == 5) {
                Kickstarter.getInstanceOf(DragonBallContainer.class).releaseNextDragonBall();
            }
            if (playTimeInSeconds < 60) {
                this.setText("Time: " + playTimeInSeconds + "s");
            } else {
                long totalMinutes = playTimeInSeconds / 60;
                long remainderSeconds = playTimeInSeconds % 60;
                this.setBounds(350, 0, 250,75);
                this.setText("Time: " + totalMinutes + "m " + remainderSeconds + "s");
            }
        });
        timer.start();
    }

    public void stopTime() {
        if (timer == null) throw new RuntimeException("No timer has started");
        timer.stop();
    }

    public LocalTime getPlayTime() {
        return LocalTime.ofSecondOfDay(playTimeInSeconds);
    }

    public void reset() {
        if (timer != null)
            timer.stop();
    }
}
