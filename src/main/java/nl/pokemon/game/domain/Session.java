package nl.pokemon.game.domain;

import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.model.GameScreen;
import nl.pokemon.game.client.view.DBCount;
import nl.pokemon.game.client.view.EndGamePanel;
import nl.pokemon.game.core.model.ScoreData;
import nl.pokemon.game.client.view.TimeBox;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.characters.Goku;
import nl.pokemon.game.core.model.dragonballs.DragonBallContainer;
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
    private EndGamePanel endGamePanel;

    @Inject
    private DragonBallContainer dragonBallContainer;

    @Inject
    private DBCount dbCount;

    @Inject
    GameScreen gameScreen;

    @Inject

    private User user;

    public Session() {
        User user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void start() {
        this.user = new User("Daniel", new Goku(), new MapCoordination(46, 63, 0, AreaType.PLAYER_BOTTOM));
        Kickstarter.getInstanceOf(FullMapService.class).setUserPosition(this.user);
        dbCount.reset();
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
