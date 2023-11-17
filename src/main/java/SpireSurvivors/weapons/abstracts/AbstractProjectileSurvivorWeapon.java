package SpireSurvivors.weapons.abstracts;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;

public abstract class AbstractProjectileSurvivorWeapon extends AbstractSurvivorWeapon {
    public AbstractProjectileSurvivorWeapon(int damage, float attackDelay, float size) {
        super(damage, attackDelay, size);
    }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle()));
    }
}
