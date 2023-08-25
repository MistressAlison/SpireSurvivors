package SpireSurvivors.entity;

import SpireSurvivors.weapons.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class AbstractSurvivorEntity {
    public ArrayList<AbstractSurvivorWeapon> weapons = new ArrayList<>();
    public float speed;
    abstract void damage(AbstractSurvivorEntity source, AbstractSurvivorWeapon weapon);
    public void update() {
        for (AbstractSurvivorWeapon w : weapons) {
            w.update();
        }
    }
    abstract void render(SpriteBatch sb);
}
