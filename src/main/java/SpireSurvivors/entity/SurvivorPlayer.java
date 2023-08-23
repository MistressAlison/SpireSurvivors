package SpireSurvivors.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class SurvivorPlayer {
    public AbstractPlayer basePlayer;

    public SurvivorPlayer(AbstractPlayer basePlayer) {
        this.basePlayer = basePlayer;
        this.basePlayer.drawX = Settings.WIDTH/2f;
        this.basePlayer.drawY = Settings.HEIGHT/2f;
        this.basePlayer.hb.move(Settings.WIDTH/2f, Settings.HEIGHT/2f);
    }

    public void update() {
        this.basePlayer.flipHorizontal = InputHelper.mX < basePlayer.hb.cX;
    }

    public void render(SpriteBatch sb) {
        basePlayer.renderPlayerImage(sb);
        basePlayer.hb.render(sb);
    }
}
