package SpireSurvivors.entity;

import SpireSurvivors.dungeon.SurvivorDungeon;
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

public class SurvivorMonster {
    public AbstractMonster monster;
    public float speed = 1f;
    public SurvivorMonster(AbstractMonster monster) {
        this.monster = monster;
    }

    public void update() {
        Vector2 dir = new Vector2(SurvivorDungeon.player.basePlayer.hb.cX - monster.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY - monster.hb.cY);
        dir.nor();
        dir.scl(speed);
        monster.drawX += dir.x;
        monster.drawY += dir.y;
        monster.hb.move(monster.hb.cX + dir.x, monster.hb.cY + dir.y);
    }

    public void render(SpriteBatch sb) {
        Texture img = ReflectionHacks.getPrivate(monster, AbstractMonster.class, "img");
        Skeleton skeleton = ReflectionHacks.getPrivate(monster, AbstractCreature.class, "skeleton");
        if (ReflectionHacks.getPrivate(monster, AbstractCreature.class, "atlas") == null) {// 863
            sb.setColor(monster.tint.color);// 864
            if (img != null) {// 865
                sb.draw(img, monster.drawX - (float)img.getWidth() * Settings.scale / 2.0F + monster.animX, monster.drawY + monster.animY, (float)img.getWidth() * Settings.scale, (float)img.getHeight() * Settings.scale, 0, 0, img.getWidth(), img.getHeight(), monster.flipHorizontal, monster.flipVertical);// 866 868 870 871 874 875
            }
        } else {
            monster.state.update(Gdx.graphics.getDeltaTime());// 880
            monster.state.apply(skeleton);// 881
            skeleton.updateWorldTransform();// 882
            skeleton.setPosition(monster.drawX + monster.animX, monster.drawY + monster.animY);// 883
            skeleton.setColor(monster.tint.color);// 884
            skeleton.setFlip(monster.flipHorizontal, monster.flipVertical);// 885
            sb.end();// 886
            CardCrawlGame.psb.begin();// 887
            AbstractCreature.sr.draw(CardCrawlGame.psb, skeleton);// 888
            CardCrawlGame.psb.end();// 889
            sb.begin();// 890
            sb.setBlendFunction(770, 771);// 891
            monster.renderHealth(sb);
        }
        monster.hb.render(sb);
    }
}
