package SpireSurvivors.weapons.abstracts;

import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public abstract class AbstractSurvivorWeapon {
    public static final float CX = Settings.WIDTH/2f;
    public static final float CY = Settings.HEIGHT/2f;
    public float size;
    public float attackDelay;
    public int damage;
    protected float timer;

    public AbstractSurvivorWeapon(int damage, float attackDelay, float size) {
        this.damage = damage;
        this.attackDelay = this.timer = attackDelay;
        this.size = size;
    }

    public void update() {
        timer -= Gdx.graphics.getDeltaTime();
        if (timer <= 0f) {
            timer = attackDelay/SurvivorDungeon.player.attackspeedModifier;
            Vector2 dir = new Vector2(InputHelper.mX - CX, InputHelper.mY - CY);
            attack(dir);
        }
    }

    public abstract void attack(Vector2 lookdir);

    public abstract void upgrade();
}
