package SpireSurvivors.ui;

import SpireSurvivors.util.lerp.LerpingColor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class MovementTutorialKey {
    public static final Texture BLANK_KEY = new Texture("SpireSurvivorsResources/images/ui/keys/blank.png");
    public static final Color BG_COLOR = Color.WHITE.cpy().add(0f, 0f, 0f, -0.2f);
    public static final Color INIT_TEXT_COLOR = Color.WHITE.cpy().add(0f, 0f, 0f, -0.2f);
    public static final Color ACTIVE_TEXT_COLOR = Color.GOLD.cpy().add(0f, 0f, 0f, -0.1f);
    public static final Color FLASH_TEXT_COLOR = Color.LIME.cpy();
    public static final float LERP_LENGTH = 0.1f;
    public final int key;
    public LerpingColor text_color = new LerpingColor(INIT_TEXT_COLOR.cpy(), LERP_LENGTH);
    public boolean justPressed = false;
    public boolean pressed = false;

    public MovementTutorialKey(int key) {
        this.key = key;
    }

    public void render(SpriteBatch sb, float x, float y, float alpha_scale) {
        text_color.update();

        if (!pressed && justPressed) {
            text_color.value = FLASH_TEXT_COLOR.cpy();
            text_color.setTarget(ACTIVE_TEXT_COLOR);
            pressed = true;
        }

        sb.setColor(BG_COLOR.cpy().mul(1f, 1f, 1f, alpha_scale));
        sb.draw(BLANK_KEY, x - BLANK_KEY.getWidth() / 2f, y - BLANK_KEY.getHeight() / 2f);
        FontHelper.renderFontCentered(
            sb, FontHelper.buttonLabelFont,
            Input.Keys.toString(key), x, y,
            text_color.value.cpy().mul(1f, 1f, 1f, alpha_scale));
    }
}
