package SpireSurvivors.entity;

import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;

public abstract class AbstractSurvivorEntity {
    public ArrayList<AbstractSurvivorWeapon> weapons = new ArrayList<>();
    public float speed;
    public Polygon hitbox;
    abstract void damage(AbstractSurvivorEntity source, AbstractSurvivorWeapon weapon);
    public void update() {
        for (AbstractSurvivorWeapon w : weapons) {
            w.update();
        }
    }
    abstract void render(SpriteBatch sb);
}
