package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.purple.EmptyBody;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class PickupRange extends AbstractStatCard {
    public final static String ID = makeID(PickupRange.class.getSimpleName());
    public PickupRange() {
        super(ID, new EmptyBody());
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.pickupRangeMultiplier += 0.25f;
    }
}
