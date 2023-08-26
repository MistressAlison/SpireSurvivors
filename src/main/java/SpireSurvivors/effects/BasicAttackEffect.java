package SpireSurvivors.effects;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorMonster;
import SpireSurvivors.util.PolygonHelper;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class BasicAttackEffect extends FlashAtkImgEffect {
    public ArrayList<AbstractSurvivorMonster> hits = new ArrayList<>();
    public Polygon hitbox;
    public AbstractSurvivorWeapon weapon;
    public BasicAttackEffect(AbstractSurvivorWeapon weapon, float x, float y, AbstractGameAction.AttackEffect effect) {
        super(x, y, effect);
        Hitbox hb = new Hitbox(img.packedWidth*Settings.scale, img.packedHeight*Settings.scale);
        hb.move(x, y);
        this.weapon = weapon;
        this.hitbox = PolygonHelper.fromHitbox(hb);
    }

    @Override
    public void update() {
        super.update();
        //Don't damage while fading out
        if (duration > startingDuration/2f) {
            for (AbstractSurvivorMonster m : SurvivorDungeon.monsters) {
                if (!hits.contains(m) && PolygonHelper.collides(hitbox, m.hitbox)) {
                    hits.add(m);
                    m.damage(SurvivorDungeon.player, weapon);
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        PolygonHelper.renderPolygon(hitbox, sb);
    }
}
