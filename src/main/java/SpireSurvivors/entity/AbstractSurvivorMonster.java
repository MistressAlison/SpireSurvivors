package SpireSurvivors.entity;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.weapons.AbstractSurvivorWeapon;
import SpireSurvivors.weapons.monster.MonsterCollisionWeapon;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

public abstract class AbstractSurvivorMonster extends AbstractSurvivorEntity {
    public AbstractMonster monster;
    public MonsterCollisionWeapon collisionWeapon;

    public AbstractSurvivorMonster(AbstractMonster m, int collisionDamage, float moveSpeed) {
        monster = m;
        monster.hb.height /= 4f;
        monster.hb.width /= 4f;
        monster.hb.move(monster.drawX, monster.drawY-monster.hb.height*3/2);
        monster.showHealthBar();
        collisionWeapon = new MonsterCollisionWeapon(collisionDamage, 1f, 1);
        speed = moveSpeed;
    }

    public void move(float dx, float dy) {
        monster.drawX += dx;
        monster.drawY += dy;
        monster.hb.move(monster.hb.cX + dx, monster.hb.cY + dy - monster.hb.height*3/2);
    }

    @Override
    public void damage(AbstractSurvivorEntity source, AbstractSurvivorWeapon weapon) {
        monster.currentHealth -= weapon.damage;
        monster.healthBarUpdatedEvent();
        SurvivorDungeon.effectsQueue.add(new StrikeEffect(monster, monster.hb.cX, monster.hb.cY, weapon.damage));
        if (monster.currentHealth <= 0) {
            monster.useSlowAttackAnimation();
            monster.isDead = true;
        }
    }

    @Override
    public void update() {
        super.update();
        monster.update();
        movementUpdate();
        collisionWeapon.update();
        if (collisionWeapon.offCooldown() && monster.hb.intersects(SurvivorDungeon.player.basePlayer.hb)) {
            SurvivorDungeon.player.damage(this, collisionWeapon);
            collisionWeapon.onHit();
        }
    }

    public abstract void movementUpdate();

    @Override
    public void render(SpriteBatch sb) {
        Texture img = ReflectionHacks.getPrivate(monster, AbstractMonster.class, "img");
        Skeleton skeleton = ReflectionHacks.getPrivate(monster, AbstractCreature.class, "skeleton");
        if (ReflectionHacks.getPrivate(monster, AbstractCreature.class, "atlas") == null) {
            sb.setColor(monster.tint.color);
            if (img != null) {
                sb.draw(img, monster.drawX - (float)img.getWidth() * Settings.scale / 2.0F + monster.animX, monster.drawY + monster.animY, (float)img.getWidth() * Settings.scale, (float)img.getHeight() * Settings.scale, 0, 0, img.getWidth(), img.getHeight(), monster.flipHorizontal, monster.flipVertical);
            }
        } else {
            monster.state.update(Gdx.graphics.getDeltaTime());
            monster.state.apply(skeleton);
            skeleton.updateWorldTransform();
            skeleton.setPosition(monster.drawX + monster.animX, monster.drawY + monster.animY);
            skeleton.setColor(monster.tint.color);
            skeleton.setFlip(monster.flipHorizontal, monster.flipVertical);
            sb.end();
            CardCrawlGame.psb.begin();
            AbstractCreature.sr.draw(CardCrawlGame.psb, skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
            sb.setBlendFunction(770, 771);
        }
        monster.renderHealth(sb);
        monster.hb.render(sb);
    }
}
