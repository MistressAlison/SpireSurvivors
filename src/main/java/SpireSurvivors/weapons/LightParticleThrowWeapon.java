package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.util.PolygonHelper;
import SpireSurvivors.weapons.abstracts.AbstractProjectileSurvivorWeapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class LightParticleThrowWeapon extends AbstractProjectileSurvivorWeapon {
    public LightParticleThrowWeapon() {
        super(1, 0.1f, 2);
    }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        int x = (int) (SurvivorDungeon.player.basePlayer.hb.cX - (float) ImageMaster.DECK_GLOW_1.packedWidth / 2.0F);
        int y = (int) (SurvivorDungeon.player.basePlayer.hb.cY - (float) ImageMaster.DECK_GLOW_1.packedHeight / 2.0F);
        Polygon hitbox = PolygonHelper.fromPosition(x, y, 150f * Settings.scale, 10f * Settings.scale, lookdir.angle(), 100f * Settings.scale, 5f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle(), 2000F, 0.25F, 0.5f, Color.PURPLE.cpy(), ImageMaster.GLOW_SPARK_2, hitbox));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
