package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractProjectileSurvivorWeapon;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class LightParticleThrowWeapon extends AbstractProjectileSurvivorWeapon {
    public static final String ID = makeID(LightParticleThrowWeapon.class.getSimpleName());

    public LightParticleThrowWeapon() {
        super(ID, new BeamCell(), 1, 0.1f, 1);
    }

    @Override
    public void attack(Vector2 lookdir) {
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle(), 1000F, 0.75F, 1.5f, Color.PURPLE.cpy(), ImageMaster.GLOW_SPARK_2));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }

    @Override
    public AbstractSurvivorWeapon makeCopy() {
        return new LightParticleThrowWeapon();
    }
}
