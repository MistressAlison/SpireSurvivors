package SpireSurvivors.cards.abstracts;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.patches.TypeOverridePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public abstract class AbstractStatCard extends AbstractSurvivorCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SpireSurvivorsMod.makeID("AbstractStatCard"));
    public static final String[] TEXT = uiStrings.TEXT;
    public AbstractStatCard(String id, TextureAtlas.AtlasRegion r) {
        super(id, "", new VoidCard(), r, "", SurvivorCardType.STAT);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, TEXT[0]);
    }

    @Override
    public void upgrade() {}
}
