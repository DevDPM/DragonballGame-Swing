package nl.pokemon.game.repository;

import nl.pokemon.game.model.core.model.ScoreData;
import org.dpmFramework.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreRepository {

    private List<ScoreData> topScoreList = new ArrayList<>();

    public void saveScore(ScoreData scoreData) {
        topScoreList.add(scoreData);
    }

    public List<ScoreData> getTopScoreList() {
        return topScoreList;
    }
}
