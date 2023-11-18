package SpireSurvivors.cards.abstracts;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractWeaponCard extends AbstractSurvivorCard {
    public AbstractSurvivorWeapon linkedWeapon;
    public AbstractWeaponCard(String id, AbstractCard artCard, AbstractSurvivorWeapon weapon) {
        super(id, "", artCard, "", SurvivorCardType.WEAPON);
        this.linkedWeapon = weapon;
    }

    @Override
    public void upgrade() {
        linkedWeapon.upgrade();
    }

    @Override
    public void onPickup(AbstractSurvivorPlayer p) {

    }
}
