package SpireSurvivors.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class LerpingColor {
    public Color value;
    private Color target;
    public float lerpLength;
    private float lerpTime;

    public LerpingColor(Color initial_color, float lerp_length) {
        value = initial_color;
        this.lerpLength = lerp_length;
    }

    public void update() {
        if (value.equals(target) || target == null) return;
        if (lerpLength == 0) value = target;
        lerpTime += Gdx.graphics.getDeltaTime();
        value = value.lerp(target, Math.min(lerpTime / lerpLength, 1));
    }

    public void setTarget(Color target) {
        lerpTime = 0f;
        this.target = target;
    }
}
