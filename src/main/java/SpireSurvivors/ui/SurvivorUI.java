package SpireSurvivors.ui;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SurvivorUI {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SpireSurvivorsMod.makeID("SurvivorUI"));
    public static final String[] TEXT = uiStrings.TEXT;
    public static final float DRAW_X = 50f * Settings.scale;
    public static final float DRAW_Y = Settings.HEIGHT - 50f * Settings.scale;
    public static final float DELTA_Y = 25f * Settings.scale;

    public void update() {

    }

    public void render(SpriteBatch sb) {
        float y = DRAW_Y;
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, SurvivorDungeon.player.basePlayer.getLoadout().name, DRAW_X, y, Color.GOLD);
        y -= DELTA_Y;
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, TEXT[0] + SurvivorDungeon.player.basePlayer.currentHealth+"/"+SurvivorDungeon.player.basePlayer.maxHealth, DRAW_X, y, Color.GOLD);
        y -= DELTA_Y;
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, TEXT[1] + SurvivorDungeon.player.currentXP, DRAW_X, y, Color.GOLD);
        y -= DELTA_Y;
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, TEXT[2] + SurvivorDungeon.player.currentLevel, DRAW_X, y, Color.GOLD);
        //y -= DELTA_Y;

        //TODO Render weapons and relics once those are coded
    }
}
