package nl.pokemon.game.core.model.dragonballs;

import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.tiles.ItemTile;

public class DragonBall {
    private MapCoordination mapCoordination;
    private int dragonballNo;
    private ItemTile item;

    public DragonBall(MapCoordination mapCoordination, ItemTile item) {
        this.mapCoordination = mapCoordination;
        this.item = item;
        this.dragonballNo = getDragonBallNumber(item);
    }

    private int getDragonBallNumber(ItemTile item) {
        return switch (item.getSqmId()) {
            case 10 -> 1;
            case 11 -> 2;
            case 12 -> 3;
            case 13 -> 4;
            case 14 -> 5;
            case 15 -> 6;
            case 16 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + item.getSqmId());
        };
    }

    public ItemTile getItem() {
        return item;
    }

    public MapCoordination getMapCoordination() {
        return mapCoordination;
    }

    public int getDragonballNo() {
        return dragonballNo;
    }
}
