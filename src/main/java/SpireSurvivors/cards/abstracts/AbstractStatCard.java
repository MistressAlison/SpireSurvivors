package SpireSurvivors.cards.abstracts;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractStatCard extends AbstractSurvivorCard {
    protected final CardStrings cardStrings;
    public AbstractStatCard(String id, AbstractCard artCard) {
        super(id, "", artCard, "", SurvivorCardType.STAT);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void upgrade() {}
}
