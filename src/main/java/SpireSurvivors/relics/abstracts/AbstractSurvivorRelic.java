package SpireSurvivors.relics.abstracts;

import SpireSurvivors.cards.abstracts.AbstractRelicCard;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSurvivorRelic {
    public final AbstractRelicCard card;
    public final String id;
    public final CardStrings cardStrings;
    public int timesUpgraded;

    public AbstractSurvivorRelic(String id, AbstractCard artCard) {
        this.id = id;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.card = new AbstractRelicCard(this, artCard) {
            @Override
            public void onSelect(AbstractSurvivorPlayer p) {
                onEquip(p);
            }
        };
    }

    public abstract void upgrade();

    public abstract AbstractSurvivorRelic makeCopy();

    public boolean canUpgrade() {
        return timesUpgraded < 5;
    }

    public boolean canRoll(AbstractSurvivorPlayer p) {
        return p.relics.stream().noneMatch(r -> r.id.equals(this.id) && !r.canUpgrade());
    }

    public void onEquip(AbstractSurvivorPlayer p) {
        List<AbstractSurvivorRelic> rels = p.relics.stream().filter(r -> r.id.equals(this.id)).collect(Collectors.toList());
        if (!rels.isEmpty()) {
            rels.get(0).upgrade();
            timesUpgraded++;
        } else {
            p.relics.add(makeCopy());
        }
    }

    public void onUnequip(AbstractSurvivorPlayer p) { }

    public void render() {

    }
}
