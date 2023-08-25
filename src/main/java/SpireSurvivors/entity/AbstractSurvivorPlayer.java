package SpireSurvivors.entity;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.patches.CardCrawlGamePatches;
import SpireSurvivors.weapons.AbstractSurvivorWeapon;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import java.lang.reflect.Field;

public abstract class AbstractSurvivorPlayer extends AbstractSurvivorEntity {
    public AbstractPlayer basePlayer;

    public AbstractSurvivorPlayer(AbstractPlayer p) {
        speed = 5f;
        basePlayer = p;
        basePlayer.drawX = Settings.WIDTH/2f;
        basePlayer.drawY = Settings.HEIGHT/2f;
        basePlayer.hb.width /= 4f;
        basePlayer.hb.height /= 4f;
        basePlayer.hb.move(Settings.WIDTH/2f, Settings.HEIGHT/2f+basePlayer.hb.height/2f);
        basePlayer.showHealthBar();

        if (basePlayer instanceof CustomPlayer) {
            AbstractAnimation animation = ReflectionHacks.getPrivate(basePlayer, CustomPlayer.class, "animation");
            if (animation instanceof SpriterAnimation) {
                ((SpriterAnimation) animation).myPlayer.setScale(((SpriterAnimation) animation).myPlayer.getScale()*0.5f);
                if (!animation.getClass().equals(SpriterAnimation.class)) {
                    for (Field f : animation.getClass().getFields()) {
                        if (f.getType().equals(Player.class)) {
                            f.setAccessible(true);
                            try {
                                Player spriterPlayer = (Player) f.get(animation);
                                spriterPlayer.setScale(spriterPlayer.getScale()*0.5f);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void damage(AbstractSurvivorEntity attacker, AbstractSurvivorWeapon weapon) {
        basePlayer.currentHealth -= weapon.damage;
        basePlayer.healthBarUpdatedEvent();
        SurvivorDungeon.effectsQueue.add(new StrikeEffect(basePlayer, basePlayer.hb.cX, basePlayer.hb.cY, weapon.damage));

        if (basePlayer.currentHealth <= 0) {
            CardCrawlGame.music.dispose();
            CardCrawlGame.sound.play("DEATH_STINGER", true);
            String bgmKey = null;
            switch (MathUtils.random(0, 3)) {
                case 0:
                    bgmKey = "STS_DeathStinger_1_v3_MUSIC.ogg";
                    break;
                case 1:
                    bgmKey = "STS_DeathStinger_2_v3_MUSIC.ogg";
                    break;
                case 2:
                    bgmKey = "STS_DeathStinger_3_v3_MUSIC.ogg";
                    break;
                case 3:
                    bgmKey = "STS_DeathStinger_4_v3_MUSIC.ogg";
                    break;
            }
            CardCrawlGame.music.playTempBgmInstantly(bgmKey, false);
            basePlayer.playDeathAnimation();
            CardCrawlGame.startOver();
            CardCrawlGamePatches.survivorGame = null;
        }
    }

    @Override
    public void update() {
        super.update();
        this.basePlayer.flipHorizontal = InputHelper.mX < basePlayer.hb.cX;
        ReflectionHacks.privateMethod(AbstractCreature.class, "updateHealthBar").invoke(basePlayer);
    }

    @Override
    public void render(SpriteBatch sb) {
        basePlayer.renderPlayerImage(sb);
        basePlayer.renderHealth(sb);
        basePlayer.hb.render(sb);
    }
}
