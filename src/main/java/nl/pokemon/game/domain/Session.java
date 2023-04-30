package nl.pokemon.game.domain;

import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.client.GameScreen;
import nl.pokemon.game.view.gamePanel.Counter;
import nl.pokemon.game.view.gamePanel.EndGamePanel;
import nl.pokemon.game.model.core.model.ScoreData;
import nl.pokemon.game.view.gamePanel.TimeBox;
import nl.pokemon.game.model.core.model.MapCoordination;
import nl.pokemon.game.model.core.model.characters.Goku;
import nl.pokemon.game.model.core.model.dragonballs.DragonBallContainer;
import nl.pokemon.game.model.core.service.FullMapService;
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
    private EndGamePanel endGamePanel;

    @Inject
    private DragonBallContainer dragonBallContainer;

    @Inject
    private Counter counter;

    @Inject
    private GameScreen gameScreen;

    @Inject
    private FullMapService fullMapService;

    private User user;

    public Session() {
        this.user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));
    }

    public User getUser() {
        return user;
    }

    public void start() {
        this.user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));
        fullMapService.setUserPosition(this.user);
        counter.reset();
        dragonBallContainer.addNewDragonBalls();
        gameScreen.updateView(this.user.getMapCoordination().getZ());
        timeBox.startTimer();
    }

    public void stop() {
        timeBox.stopTime();
        ScoreData scoreData = new ScoreData(timeBox.getPlayTime());
        timeBox.reset();
        scoreRepository.saveScore(scoreData);
        endGamePanel.writeDataOnScreen(scoreData);
        FullMap.erasePosition(this.user.getMapCoordination());
    }
}
