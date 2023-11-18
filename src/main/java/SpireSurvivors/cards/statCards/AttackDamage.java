package SpireSurvivors.cards.statCards;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class AttackDamage extends AbstractStatCard {
    public final static String ID = makeID(AttackDamage.class.getSimpleName());
    public AttackDamage() {
        super(ID, AbstractPower.atlas.findRegion("128/strength"));
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {
        p.damageModifier += 0.1f;
    }
}
