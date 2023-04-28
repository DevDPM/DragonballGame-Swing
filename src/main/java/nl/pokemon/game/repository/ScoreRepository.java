package nl.pokemon.game.repository;

import nl.pokemon.game.core.model.ScoreData;
import nl.pokemon.game.domain.User;
import org.dpmFramework.annotation.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
