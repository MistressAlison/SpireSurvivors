package SpireSurvivors.ui;

import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;

public class MovementTutorial {
    public static final float MOVEMENT_KEY_MARGIN = 2f;

    public MovementTutorialKey up = new MovementTutorialKey(SurvivorDungeon.UP.getKey());
    public MovementTutorialKey down = new MovementTutorialKey(SurvivorDungeon.DOWN.getKey());
    public MovementTutorialKey right = new MovementTutorialKey(SurvivorDungeon.RIGHT.getKey());
    public MovementTutorialKey left = new MovementTutorialKey(SurvivorDungeon.LEFT.getKey());

    public static final float LERP_LENGTH = 0.1f;
    private float lerpTime = 0f;
    public float alpha = 1f;
    private float targetAlpha = 1f;

    public void setTargetAlpha(float target) {
        lerpTime = 0f;
        targetAlpha = target;
    }

    public void render(SpriteBatch sb, float x, float y) {
        sb.setColor(Color.WHITE);

        if (alpha != targetAlpha) {
            alpha = MathUtils.lerp(alpha, targetAlpha, Math.min((lerpTime += Gdx.graphics.getDeltaTime()) / LERP_LENGTH, 1f));
        }

        if (up.pressed && down.pressed && right.pressed && left.pressed) {
            setTargetAlpha(0f);
        }

        float top_y = y + MOVEMENT_KEY_MARGIN/2f + MovementTutorialKey.BLANK_KEY.getHeight()/2f;
        float bot_y = y - MOVEMENT_KEY_MARGIN/2f - MovementTutorialKey.BLANK_KEY.getHeight()/2f;
        up.render(sb, x, top_y, alpha);
        down.render(sb, x, bot_y, alpha);
        right.render(sb, x + MOVEMENT_KEY_MARGIN + MovementTutorialKey.BLANK_KEY.getWidth(), bot_y, alpha);
        left.render(sb, x - MOVEMENT_KEY_MARGIN - MovementTutorialKey.BLANK_KEY.getWidth(), bot_y, alpha);
    }
}
