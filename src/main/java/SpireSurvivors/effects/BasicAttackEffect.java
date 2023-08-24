package SpireSurvivors.effects;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.SurvivorMonster;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class BasicAttackEffect extends FlashAtkImgEffect {
    public ArrayList<SurvivorMonster> hits = new ArrayList<>();
    public Hitbox hb = new Hitbox(100f * Settings.scale, 100f * Settings.scale);
    public int damage;
    public BasicAttackEffect(int damage, float x, float y, AbstractGameAction.AttackEffect effect) {
        super(x, y, effect);
        hb.resize(img.packedWidth*Settings.scale, img.packedHeight*Settings.scale);
        hb.move(x, y);
        this.damage = damage;
    }

    @Override
    public void update() {
        super.update();
        for (SurvivorMonster m : SurvivorDungeon.monsters) {
            if (m.monster.hb.intersects(hb) && !hits.contains(m)) {
                hits.add(m);
                m.damage(SurvivorDungeon.player, damage);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        hb.render(sb);
    }
}
