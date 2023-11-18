package SpireSurvivors.weapons.abstracts;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.cards.abstracts.AbstractWeaponCard;
import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractSurvivorWeapon {
    public static final float CX = Settings.WIDTH/2f;
    public static final float CY = Settings.HEIGHT/2f;
    public final AbstractWeaponCard card;
    public final String id;
    public final CardStrings cardStrings;
    public float size;
    public float attackDelay;
    public int damage;
    protected float timer;

    public AbstractSurvivorWeapon(String id, AbstractCard artCard, int damage, float attackDelay, float size) {
        this.id = id;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.card = new AbstractWeaponCard(this, artCard) {
            @Override
            public void onSelect(AbstractSurvivorPlayer p) {
                onEquip(p);
            }
        };
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

    public void onEquip(AbstractSurvivorPlayer p) { }

    public void onUnequip(AbstractSurvivorPlayer p) { }

    public void render() {

    }
}
