package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.FlyingDaggerAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.green.DaggerThrow;
import com.megacrit.cardcrawl.core.Settings;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class DaggerThrowWeapon extends AbstractSurvivorWeapon {
    public static final String ID = makeID(DaggerThrowWeapon.class.getSimpleName());


    public DaggerThrowWeapon() {
        super(ID, new DaggerThrow(), 4, 0.5f, 1);
    }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new FlyingDaggerAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle()));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
