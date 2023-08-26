package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class BashWeapon extends AbstractSurvivorWeapon {
    public BashWeapon() {
        super(8, 1.2f, 1);
    }

    @Override
    public void attack(Vector2 lookdir) {
        Vector2 aim = lookdir.cpy().clamp(10f * Settings.scale, 100f * Settings.scale);
        SurvivorDungeon.effectsQueue.add(new BasicAttackEffect(this, CX + aim.x, CY + aim.y + SurvivorDungeon.player.basePlayer.hb.height/2f, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }
}
