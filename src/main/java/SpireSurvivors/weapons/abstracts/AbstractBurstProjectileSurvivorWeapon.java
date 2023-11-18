package SpireSurvivors.weapons.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public abstract class AbstractBurstProjectileSurvivorWeapon extends AbstractSurvivorWeapon {
    float burstTimer, burstDelay;
    int burstAmount, burstsLeft;

    public AbstractBurstProjectileSurvivorWeapon(String id, AbstractCard artCard, int damage, float attackDelay, int burstAmount, float burstDelay, float size) {
        super(id, artCard, damage, attackDelay, size);
        this.burstDelay = burstDelay;
        this.burstAmount = burstAmount;
    }

    @Override
    public void update() {
        super.update();
        if (burstsLeft > 0) {
            burstTimer -= Gdx.graphics.getDeltaTime();
            if (burstTimer <= 0f) {
                burstTimer = burstDelay;
                burstsLeft--;
                Vector2 dir = new Vector2(InputHelper.mX - CX, InputHelper.mY - CY);
                doBurst(dir);
            }
        }
    }

    @Override
    public void attack(Vector2 lookdir) {
        burstsLeft = burstAmount;
    }

    public abstract void doBurst(Vector2 lookdir);
}
