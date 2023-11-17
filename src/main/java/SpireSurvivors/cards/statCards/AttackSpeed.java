package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.green.BladeDance;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class AttackSpeed extends AbstractStatCard {
    public final static String ID = makeID(AttackSpeed.class.getSimpleName());
    public AttackSpeed() {
        super(ID, new BladeDance());
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.attackspeedModifier += 0.1f;
    }
}
