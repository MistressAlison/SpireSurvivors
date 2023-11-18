package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class MovementSpeed extends AbstractStatCard {
    public final static String ID = makeID(MovementSpeed.class.getSimpleName());
    public MovementSpeed() {
        super(ID, AbstractPower.atlas.findRegion("128/flight"));
    }

    @Override
    public void onSelect(AbstractSurvivorPlayer p) {
        p.speedMultiplier += 0.25f;
    }
}
