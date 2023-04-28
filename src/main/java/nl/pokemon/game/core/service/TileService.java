package nl.pokemon.game.core.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.core.model.Elevatable;
import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.ItemTile;
import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.bootstrap.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class TileService {

    @Inject
    private FullMapService fullMapService;

    public boolean isDragonBallTile(MapCoordination mapCoordination) {
        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.LOWER_TERRAIN);

        BaseTile baseTile = fullMapService.getBaseTileByPosition(newMapCoordination);
        return baseTile instanceof ItemTile;
    }

    public ItemTile getDragonBallTile(MapCoordination mapCoordination) {
        MapCoordination newMapCoordination = new MapCoordination(mapCoordination.getX(), mapCoordination.getY(), mapCoordination.getZ(), AreaType.LOWER_TERRAIN);
        return (ItemTile) fullMapService.getBaseTileByPosition(newMapCoordination);
    }

    public BaseTile getSQMByIntAndAreaOrNull(AreaType area, int sqmId) {
        return TilesetImageContainer.getTileByIntAndArea(area, sqmId);
    }

    public Elevatable isElevatingSQMOrNull(User user, Direction direction) {
        return isElevatingSQMOrNull(getMapCoordinationByDirection(user, direction));
    }

    private Elevatable isElevatingSQMOrNull(MapCoordination mapCoordination) {
        if (validateMapOutOfRange(mapCoordination))
            return null;

        for (AreaType areaType : AreaType.values()) {
            mapCoordination.setAreaType(areaType);
            BaseTile baseTile = fullMapService.getBaseTileByPosition(mapCoordination);
            if (baseTile instanceof Elevatable elevation)
                return elevation;
        }
        return null;
    }

    public boolean isWalkableTile(User user, Direction direction) {
        return isWalkableTile(getMapCoordinationByDirection(user, direction));
    }

    private boolean isWalkableTile(MapCoordination mapCoordination) {
        if (validateMapOutOfRange(mapCoordination))
            return false;

        for (AreaType areaType : AreaType.values()) {
            mapCoordination.setAreaType(areaType);
            BaseTile baseTile = fullMapService.getBaseTileByPosition(mapCoordination);

            if (baseTile.isNotWalkable())
                return false;
        }
        return true;
    }

    private boolean validateMapOutOfRange(MapCoordination mapCoordination) {
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return true;
        return false;
    }

    private MapCoordination getMapCoordinationByDirection(User user, Direction direction) {
        MapCoordination mapCoordination = user.getMapCoordination();
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();

        if (direction != null) {
            x += direction.getX();
            y += direction.getY();
        }
        return new MapCoordination(x, y, z, mapCoordination.getAreaType());
    }

    public boolean isElevatingSQM(MapCoordination nextPosition) {
        if (validateMapOutOfRange(nextPosition))
            return false;

        MapCoordination copyOfPosition = MapCoordination.copyOf(nextPosition);
        copyOfPosition.setAreaType(Elevatable.getAreaType());
        BaseTile baseTile = fullMapService.getBaseTileByPosition(copyOfPosition);
        return baseTile instanceof Elevatable;
    }

    public BaseTile getBaseTile(MapCoordination nextPosition, AreaType areaType) {
        MapCoordination copyOfPosition = MapCoordination.copyOf(nextPosition);
        copyOfPosition.setAreaType(areaType);
        return getBaseTile(copyOfPosition);
    }

    public BaseTile getBaseTile(MapCoordination nextPosition) {
        if (validateMapOutOfRange(nextPosition))
            return null;

        return fullMapService.getBaseTileByPosition(nextPosition);
    }
}
