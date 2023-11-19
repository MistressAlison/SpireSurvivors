package SpireSurvivors.weapons;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.effects.BasicProjectileAttackEffect;
import SpireSurvivors.weapons.abstracts.AbstractBurstProjectileSurvivorWeapon;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.blue.BallLightning;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class LightningSparkWeapon extends AbstractBurstProjectileSurvivorWeapon {
    public static final String ID = makeID(LightningSparkWeapon.class.getSimpleName());

    public LightningSparkWeapon() {
        super(ID, new BallLightning(), 4, 1f, 3, 0.05f, 1f);
    }

    @Override
    public void doBurst(Vector2 lookdir) {
        SurvivorDungeon.effectsQueue.add(new BasicProjectileAttackEffect(this, SurvivorDungeon.player.basePlayer.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY, lookdir.angle(), 2000F, 1.0F, 0.75f * size, Color.YELLOW.cpy(), ImageMaster.vfxAtlas.findRegion("combat/defect/l_orb1")));
    }

    @Override
    public void upgrade() {
        damage += 2;
    }

    @Override
    public AbstractSurvivorWeapon makeCopy() {
        return new LightningSparkWeapon();
    }
}
