package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class AttackSpeed extends AbstractStatCard {
    public final static String ID = makeID(AttackSpeed.class.getSimpleName());
    public AttackSpeed() {
        super(ID, AbstractPower.atlas.findRegion("128/time"));
    }

    @Override
    public void onSelect(AbstractSurvivorPlayer p) {
        p.attackspeedModifier += 0.1f;
    }
}
