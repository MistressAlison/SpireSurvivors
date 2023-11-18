package SpireSurvivors.cards.abstracts;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractWeaponCard extends AbstractSurvivorCard {
    public AbstractWeaponCard(AbstractSurvivorWeapon weapon, AbstractCard artCard) {
        super(weapon.id, weapon.cardStrings.NAME, artCard, weapon.cardStrings.DESCRIPTION, SurvivorCardType.WEAPON);
    }
}
