package SpireSurvivors.weapons.monster;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.LightningSparkWeapon;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;

import static SpireSurvivors.SpireSurvivorsMod.makeID;

public class MonsterCollisionWeapon extends AbstractSurvivorWeapon {
    public static final String ID = makeID(MonsterCollisionWeapon.class.getSimpleName());
    public static final AbstractCard ART_CARD = new Necronomicurse();

    public MonsterCollisionWeapon() {
        this(1, 1, 1);
    }

    public MonsterCollisionWeapon(int damage, float attackDelay, float size) {
        super(ID, ART_CARD, damage, attackDelay, size);
    }

    public boolean offCooldown() {
        return timer <= 0f;
    }

    public void onHit() {
        //timer += attackDelay;
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

    @Override
    public AbstractSurvivorWeapon makeCopy() {
        return new MonsterCollisionWeapon(damage, attackDelay, size);
    }

    @Override
    public boolean canRoll(AbstractSurvivorPlayer p) {
        return false;
    }
}
