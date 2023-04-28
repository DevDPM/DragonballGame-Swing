package nl.pokemon.game.domain;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.view.EndGame;
import nl.pokemon.game.core.model.ScoreData;
import nl.pokemon.game.client.view.TimeBox;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.characters.Goku;
import nl.pokemon.game.core.service.FullMapService;
import nl.pokemon.game.repository.ScoreRepository;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class Session {

    @Inject
    private TimeBox timeBox;

    @Inject
    private ScoreRepository scoreRepository;

    @Inject
    private EndGame endGame;

    private User user;

    public Session() {
        User user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void start() {
        Kickstarter.getInstanceOf(FullMapService.class).setUserPosition(this.user);
        timeBox.startTimer();
    }

    public void stop() {
        timeBox.stopTime();
        ScoreData scoreData = new ScoreData(timeBox.getPlayTime());
        scoreRepository.saveScore(scoreData);
        endGame.writeDataOnScreen(scoreData);
    }
}
