package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractProjectileSurvivorWeapon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class HeavyOrbThrowWeapon extends AbstractProjectileSurvivorWeapon {

private float rotate;
    public HeavyOrbThrowWeapon() {
        super(16, 0.5F, 100);
    }
    public HeavyOrbThrowWeapon(float rotate) {
            super(16, 0.5F, 100);
            this.rotate=rotate;
        }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle()+rotate, ImageMaster.CARD_GRAY_ORB_L));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
