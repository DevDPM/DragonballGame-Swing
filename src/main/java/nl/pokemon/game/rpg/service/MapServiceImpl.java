package nl.pokemon.game.rpg.service;

import org.dpmFramework.annotation.Service;
import nl.pokemon.game.rpg.util.FullDataMap;

@Service
public class MapServiceImpl implements MapService {

    int startX = -20;
    int startY = -20;

    Object[][] objectMap = new Object[38][38];

    @Override
    public void generateMap() {
        int[][] dataMap = FullDataMap.generate();

//        for (int y = 0; y < dataMap[1].length; y++) {
//            for (int x = 0; x < dataMap.length; x++) {
//                if (dataMap[y][x] == 0) {
//                    objectMap[y][x] = new SQM(startX+(x*50), startY+(y*50), new Color(220, 220, 220));
//                }
//                if (dataMap[y][x] == 1) {
//                    objectMap[y][x] = new SQM(startX+(x*50), startY+(y*50), new Color(102, 192, 60));
//                }
//                if (dataMap[y][x] == 2) {
//                    objectMap[y][x] = new SQM(startX+(x*50), startY+(y*50), Color.green);
//                }
//                if (dataMap[y][x] == 3) {
//                    objectMap[y][x] = new SQM(startX+(x*50), startY+(y*50), new Color(31, 31, 31));
//                }
//                if (dataMap[y][x] == 4) {
//                    objectMap[y][x] = new SQM(startX+(y*50), startY+(x*50), Color.blue);
//                }
//            }
//        }
    }

    @Override
    public Object[][] getMap() {
        return objectMap;
    }
}
