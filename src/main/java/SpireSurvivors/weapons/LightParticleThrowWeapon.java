package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractProjectileSurvivorWeapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class LightParticleThrowWeapon extends AbstractProjectileSurvivorWeapon {
    public LightParticleThrowWeapon() {
        super(1, 0.1f, 1);
    }

    @Override
    public void attack(Vector2 lookdir) {
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle(), 1000F, 0.75F, 1.5f, Color.PURPLE.cpy(), ImageMaster.GLOW_SPARK_2));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
