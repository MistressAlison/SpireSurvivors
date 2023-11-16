package SpireSurvivors.cards.abstracts;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.util.PortraitHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractSurvivorCard extends CustomCard {
    public enum SurvivorCardType {
        WEAPON,
        RELIC,
        STAT,
        ABILITY
    }
    public AbstractSurvivorCard(String id, String name, AbstractCard artCard, String rawDescription, SurvivorCardType sType) {
        super(id, name, (String) null, -2, rawDescription, sType == SurvivorCardType.WEAPON ? CardType.ATTACK : (sType == SurvivorCardType.RELIC ? CardType.SKILL : CardType.POWER), CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        if (artCard.type != this.type) {
            artCard.type = this.type;
            PortraitHelper.setMaskedPortrait(artCard);
        }
        this.portrait = artCard.portrait;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    public abstract void onPickup(AbstractSurvivorPlayer p);
}
