package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.util.FULL_MAP;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.Map;

@Service
public class FullMapService {

    @Inject
    SQMService sqmService;

    public Map<Integer, Map<AreaType, int[][]>> getFullMap() {
        return FULL_MAP.getViewMap();
    }

    public Map<AreaType, int[][]> getFullMapLayerLevel(int z) {
        return getFullMap().get(z);
    }

    public int[][] getFullMapGridInt(AreaType areaType, int z) {
        return getFullMapLayerLevel(z).get(areaType);
    }

    public BaseSQM getFullMapSQM(AreaType areaType, int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FULL_MAP.fullMapWidth() || y >= FULL_MAP.fullMapHeight())
            return null;
        int sqmId = getFullMapGridInt(areaType, z)[y][x];
        return sqmService.getSQMByIntAndArea(areaType, sqmId);
    }
}
