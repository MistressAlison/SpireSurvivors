package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicAttackEffect;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class StrikeWeapon extends AbstractSurvivorWeapon {
    public StrikeWeapon() {
        super(6, 1, 1);
    }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new BasicAttackEffect(damage, CX + aim.x, CY + aim.y + SurvivorDungeon.player.basePlayer.hb.height/2f, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
