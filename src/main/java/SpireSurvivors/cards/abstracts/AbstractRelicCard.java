package SpireSurvivors.cards.abstracts;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractRelicCard extends AbstractSurvivorCard {
    public AbstractRelicCard(String id, String name, AbstractCard artCard, String rawDescription, SurvivorCardType sType) {
        super(id, name, artCard, rawDescription, sType);
    }
}
