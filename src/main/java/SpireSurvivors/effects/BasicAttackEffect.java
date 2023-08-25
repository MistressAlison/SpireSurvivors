package SpireSurvivors.effects;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorMonster;
import SpireSurvivors.weapons.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class BasicAttackEffect extends FlashAtkImgEffect {
    public ArrayList<AbstractSurvivorMonster> hits = new ArrayList<>();
    public Hitbox hb = new Hitbox(100f * Settings.scale, 100f * Settings.scale);
    public AbstractSurvivorWeapon weapon;
    public BasicAttackEffect(AbstractSurvivorWeapon weapon, float x, float y, AbstractGameAction.AttackEffect effect) {
        super(x, y, effect);
        hb.resize(img.packedWidth*Settings.scale, img.packedHeight*Settings.scale);
        hb.move(x, y);
        this.weapon = weapon;
    }

    @Override
    public void update() {
        super.update();
        for (AbstractSurvivorMonster m : SurvivorDungeon.monsters) {
            if (m.monster.hb.intersects(hb) && !hits.contains(m)) {
                hits.add(m);
                m.damage(SurvivorDungeon.player, weapon);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        hb.render(sb);
    }
}
