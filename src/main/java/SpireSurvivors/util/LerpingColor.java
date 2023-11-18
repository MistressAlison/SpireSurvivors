package SpireSurvivors.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class LerpingColor {
    public Color value;
    private Color target;
    public float lerp_length;
    private float lerp_time;

    public LerpingColor(Color initial_color, float lerp_length) {
        value = initial_color;
        this.lerp_length = lerp_length;
    }

    public void update() {
        if (value.equals(target) || target == null) return;
        if (lerp_length == 0) value = target;
        lerp_time += Gdx.graphics.getDeltaTime();
        value = value.lerp(target, Math.min(lerp_time/lerp_length, 1));
    }

    public void setTarget(Color target) {
        lerp_time = 0f;
        this.target = target;
    }
}
