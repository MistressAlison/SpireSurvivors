package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class PickupRange extends AbstractStatCard {
    public final static String ID = makeID(PickupRange.class.getSimpleName());
    public PickupRange() {
        super(ID, AbstractPower.atlas.findRegion("128/magnet"));
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.pickupRangeMultiplier += 0.25f;
    }
}
