package SpireSurvivors.weapons.monster;

import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MonsterCollisionWeapon extends AbstractSurvivorWeapon {
    public MonsterCollisionWeapon(int damage, float attackDelay, float size) {
        super(damage, attackDelay, size);
    }

    public boolean offCooldown() {
        return timer <= 0f;
    }

    public void onHit() {
        timer += attackDelay;
    }

    @Override
    public void update() {
        if (timer > 0f) {
            timer -= Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void attack(Vector2 lookdir) {}

    @Override
    public void upgrade() {}
}
