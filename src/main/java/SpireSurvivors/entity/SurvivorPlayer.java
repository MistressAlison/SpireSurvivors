package SpireSurvivors.entity;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.weapons.AbstractSurvivorWeapon;
import SpireSurvivors.weapons.StrikeWeapon;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SurvivorPlayer {
    public AbstractPlayer basePlayer;
    public float movementSpeed = 5f;
    public ArrayList<AbstractSurvivorWeapon> weapons = new ArrayList<>();

    public SurvivorPlayer(AbstractPlayer p) {
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

        weapons.add(new StrikeWeapon());
    }

    public void damage(SurvivorMonster attacker, int amount) {
        basePlayer.currentHealth -= amount;
        basePlayer.healthBarUpdatedEvent();
        SurvivorDungeon.effectsQueue.add(new StrikeEffect(basePlayer, basePlayer.hb.cX, basePlayer.hb.cY, amount));
    }

    public void update() {
        this.basePlayer.flipHorizontal = InputHelper.mX < basePlayer.hb.cX;
        for (AbstractSurvivorWeapon w : weapons) {
            w.update();
        }
        ReflectionHacks.privateMethod(AbstractCreature.class, "updateHealthBar").invoke(basePlayer);
    }

    public void render(SpriteBatch sb) {
        basePlayer.renderPlayerImage(sb);
        basePlayer.renderHealth(sb);
        basePlayer.hb.render(sb);
    }
}
