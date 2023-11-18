package SpireSurvivors.effects;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorMonster;
import SpireSurvivors.util.PolygonHelper;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class FlyingDaggerAttackEffect extends AbstractGameEffect {
    public ArrayList<AbstractSurvivorMonster> hits = new ArrayList<>();
    public Polygon hitbox;
    public AbstractSurvivorWeapon weapon;
    private float x;
    private float y;
    private final float scaleMultiplier;
    private static final float DUR = 0.5F;
    private final TextureAtlas.AtlasRegion img;
    private boolean playedSound = false;
    private final float velocity = 1000.0F;

    public FlyingDaggerAttackEffect(AbstractSurvivorWeapon weapon, float x, float y, float fAngle) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.5F;
        this.duration = 0.5F;
        this.scaleMultiplier = MathUtils.random(1.2F, 1.5F);
        this.scale = 0.25F * Settings.scale * SurvivorDungeon.player.attackSizeModifier;
        this.rotation = fAngle;

        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;
        Hitbox hb = new Hitbox(ImageMaster.DAGGER_STREAK.packedWidth * Settings.scale, ImageMaster.DAGGER_STREAK.packedHeight * Settings.scale);
        hb.move(x, y);
        this.weapon = weapon;
        this.hitbox = PolygonHelper.fromPosition(x, y, 150f*Settings.scale * SurvivorDungeon.player.attackSizeModifier, 10f*Settings.scale * SurvivorDungeon.player.attackSizeModifier, fAngle, 100f*Settings.scale, 5f*Settings.scale);
    }

    private void playRandomSfX() {
        int roll = MathUtils.random(5);
        switch (roll) {
            case 0:
                CardCrawlGame.sound.play("ATTACK_DAGGER_1");
                break;
            case 1:
                CardCrawlGame.sound.play("ATTACK_DAGGER_2");
                break;
            case 2:
                CardCrawlGame.sound.play("ATTACK_DAGGER_3");
                break;
            case 3:
                CardCrawlGame.sound.play("ATTACK_DAGGER_4");
                break;
            case 4:
                CardCrawlGame.sound.play("ATTACK_DAGGER_5");
                break;
            default:
                CardCrawlGame.sound.play("ATTACK_DAGGER_6");
        }

    }

    public void update() {
        if (!this.playedSound) {
            this.playRandomSfX();
            this.playedSound = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        Vector2 derp = new Vector2(MathUtils.cos(0.017453292F * this.rotation), MathUtils.sin(0.017453292F * this.rotation));
        float dx = derp.x * Gdx.graphics.getDeltaTime() * velocity * this.scaleMultiplier * Settings.scale;
        float dy =  derp.y * Gdx.graphics.getDeltaTime() * velocity * this.scaleMultiplier * Settings.scale;
        this.x += dx;
        this.y += dy;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration > 0.25F) {
            this.color.a = Interpolation.pow5In.apply(1.0F, 0.0F, (this.duration - 0.25F) * 4.0F);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration * 4.0F);
        }

        this.scale += Gdx.graphics.getDeltaTime() * this.scaleMultiplier;
        hitbox.translate(dx, dy);
        //Don't damage while fading out
        if (duration > startingDuration/8f) {
            for (AbstractSurvivorMonster m : SurvivorDungeon.monsters) {
                if (!hits.contains(m) && PolygonHelper.collides(hitbox, m.hitbox)) {
                    hits.add(m);
                    m.damage(SurvivorDungeon.player, weapon);
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale * 1.5F, this.rotation);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.75F, this.scale * 0.75F, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
