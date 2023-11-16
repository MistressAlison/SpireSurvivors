package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.blue.Turbo;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class MovementSpeed extends AbstractStatCard {
    public final static String ID = makeID(MovementSpeed.class.getSimpleName());
    public MovementSpeed() {
        super(ID, new Turbo());
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.speedMultiplier += 0.25f;
    }
}
