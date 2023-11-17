package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.green.Accuracy;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class CritChance extends AbstractStatCard {
    public final static String ID = makeID(CritChance.class.getSimpleName());
    public CritChance() {
        super(ID, new Accuracy());
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.critChance += 0.1f;
    }
}
