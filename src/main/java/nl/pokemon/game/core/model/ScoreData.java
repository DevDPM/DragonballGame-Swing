package nl.pokemon.game.core.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScoreData {
    private LocalDate date;
    private LocalTime time;

    public ScoreData(LocalTime time) {
        this.date = LocalDate.now();
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
