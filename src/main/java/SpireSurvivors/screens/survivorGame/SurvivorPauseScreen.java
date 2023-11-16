package SpireSurvivors.screens.survivorGame;

import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class SurvivorPauseScreen {
    private static final Color screenColor = Color.BLACK.cpy();
    private static float screenTimer;
    private static float screenTime;

    public void update() {
        if (SurvivorDungeon.PAUSE.isJustPressed()) {
            SurvivorDungeon.isScreenUp = false;
            SurvivorDungeon.currentScreen = SurvivorDungeon.CurrentScreen.NONE;
        }
        if (screenTimer != 0.0F) {// 504
            screenTimer -= Gdx.graphics.getDeltaTime();// 505
            if (screenTimer < 0.0F) {// 506
                screenTimer = 0.0F;// 507
            }
            screenColor.a = Interpolation.fade.apply(0.0F, 0.5F, 1.0F - screenTimer / screenTime);
        }
    }
    
    public void open(boolean animated) {
        SurvivorDungeon.isScreenUp = true;
        SurvivorDungeon.currentScreen = SurvivorDungeon.CurrentScreen.PAUSE;
        screenColor.a = 0.0f;
        if (animated) {
            screenTime = screenTimer = 1f;
        } else {
            screenColor.a = 0.5f;
        }
    }    

    public void render(SpriteBatch sb) {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float)Settings.HEIGHT);
    }
}
