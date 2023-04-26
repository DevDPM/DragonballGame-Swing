package nl.pokemon.game.model.dragonballs;

import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.SQMs.ItemSQM;

public class DragonBall {
    private MapCoordination mapCoordination;
    private int dragonballNo;
    private ItemSQM item;

    public DragonBall(MapCoordination mapCoordination, ItemSQM item) {
        this.mapCoordination = mapCoordination;
        this.item = item;
        this.dragonballNo = getDragonBallNumber(item);
    }

    private int getDragonBallNumber(ItemSQM item) {
        return switch (item.getSqmId()) {
            case 41 -> 1;
            case 42 -> 2;
            case 43 -> 3;
            case 44 -> 4;
            case 45 -> 5;
            case 46 -> 6;
            case 47 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + item.getSqmId());
        };
    }

    public ItemSQM getItem() {
        return item;
    }

    public MapCoordination getMapCoordination() {
        return mapCoordination;
    }

    public int getDragonballNo() {
        return dragonballNo;
    }
}
