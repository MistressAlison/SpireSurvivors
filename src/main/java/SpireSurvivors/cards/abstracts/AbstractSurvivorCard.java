package SpireSurvivors.cards.abstracts;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.util.PortraitHelper;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractSurvivorCard extends CustomCard {
    public enum SurvivorCardType {
        WEAPON,
        RELIC,
        STAT,
        ABILITY
    }
    protected final CardStrings cardStrings;
    public AbstractSurvivorCard(String id, String name, AbstractCard artCard, String rawDescription, SurvivorCardType sType) {
        this(id, name, artCard, null, rawDescription, sType);
    }

    public AbstractSurvivorCard(String id, String name, AbstractCard artCard, TextureAtlas.AtlasRegion r, String rawDescription, SurvivorCardType sType) {
        super(id, name, (String) null, -2, rawDescription, sType == SurvivorCardType.WEAPON ? CardType.ATTACK : (sType == SurvivorCardType.RELIC ? CardType.SKILL : CardType.POWER), CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        if (artCard.type != this.type || r != null) {
            artCard.type = this.type;
            PortraitHelper.setMaskedPortrait(artCard, r);
        }
        this.portrait = artCard.portrait;
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    public abstract void onPickup(AbstractSurvivorPlayer p);
}
