package SpireSurvivors.cards.abstracts;

import SpireSurvivors.relics.abstracts.AbstractSurvivorRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractRelicCard extends AbstractSurvivorCard {
    public AbstractRelicCard(AbstractSurvivorRelic relic, AbstractCard artCard) {
        super(relic.id, relic.cardStrings.NAME, artCard, relic.cardStrings.DESCRIPTION, SurvivorCardType.RELIC);
    }
}
