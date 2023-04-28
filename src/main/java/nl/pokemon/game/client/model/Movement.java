package nl.pokemon.game.client.model;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.service.Player;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.Map;

@Service
public class Movement {

    public static final int SPEED_PIXEL_PER_ITERATE = 10; // 50 = max

    private int smoothMovePosX = 0;
    private int smoothMovePosY = 0;

    @Inject
    Player player;

    @Inject
    GameScreen gameScreen;

    public void moveScreenByDirection(Direction direction) {
        setPixelMovement(direction);

        gameScreen.getFullViewMap().keySet().forEach((z) -> {
            Map<AreaType, TileMap> viewSurfaceGridMap = gameScreen.getViewGridMapLayerLevel(z);

            for (AreaType areaType : AreaType.values()) {
                BaseTile[][] viewGridMap = viewSurfaceGridMap.get(areaType).getTileMap();

                for (int y = 0; y < TileMap.MAX_Y; y++) {
                    for (int x = 0; x < TileMap.MAX_X; x++) {

                        BaseTile viewSQM = viewGridMap[y][x];
                        MapCoordination userCoordinate = player.getUserCoordination();
                        if (!((x == 10 && y == 10 && z == userCoordinate.getZ()) &&
                                areaType.equals(userCoordinate.getAreaType()))) {
                            int newPixelPosX;
                            int newPixelPosY;
                            newPixelPosX = viewSQM.getPixelPosXByIndex() + smoothMovePosX;
                            newPixelPosY = viewSQM.getPixelPosYByIndex() + smoothMovePosY;
                            viewSQM.moveSQM(newPixelPosX, newPixelPosY);
                        }
                    }
                }
            }
        });
    }

    private void setPixelMovement(Direction direction) {
        this.smoothMovePosX = 0;
        this.smoothMovePosY = 0;

        switch (direction) {
            case NORTH -> smoothMovePosY = SPEED_PIXEL_PER_ITERATE;
            case EAST -> smoothMovePosX = -SPEED_PIXEL_PER_ITERATE;
            case SOUTH -> smoothMovePosY = -SPEED_PIXEL_PER_ITERATE;
            case WEST -> smoothMovePosX = SPEED_PIXEL_PER_ITERATE;
        }
    }
}
