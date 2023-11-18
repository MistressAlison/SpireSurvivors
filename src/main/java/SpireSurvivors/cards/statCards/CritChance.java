package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class CritChance extends AbstractStatCard {
    public final static String ID = makeID(CritChance.class.getSimpleName());
    public CritChance() {
        super(ID, AbstractPower.atlas.findRegion("128/accuracy"));
    }

    @Override
    public void onSelect(AbstractSurvivorPlayer p) {
        p.critChance += 0.1f;
    }
}
