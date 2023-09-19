package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractBurstProjectileSurvivorWeapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class FlamethrowerWeapon extends AbstractBurstProjectileSurvivorWeapon {
    public FlamethrowerWeapon() {
        super(1, 1f, 10, 0.05f, 1f);
    }

    @Override
    public void doBurst(Vector2 lookdir) {
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle(), 2000F, 0.25F, 0.5f, Color.RED.cpy(), ImageMaster.FLAME_1));

    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
