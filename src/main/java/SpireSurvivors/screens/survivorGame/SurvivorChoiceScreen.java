package SpireSurvivors.screens.survivorGame;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.cards.abstracts.AbstractSurvivorCard;
import SpireSurvivors.cards.statCards.AttackSpeed;
import SpireSurvivors.cards.statCards.CritChance;
import SpireSurvivors.cards.statCards.MovementSpeed;
import SpireSurvivors.cards.statCards.PickupRange;
import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class SurvivorChoiceScreen {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SpireSurvivorsMod.makeID("SurvivorChoiceScreen"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final Color screenColor = Color.BLACK.cpy();
    private static final float PAD_X = 40.0F * Settings.xScale;// 53
    private static final float CARD_TARGET_Y = (float)Settings.HEIGHT * 0.45F;// 54
    private static final float START_X = (float)Settings.WIDTH - 300.0F * Settings.xScale;
    private static float screenTimer;
    private static float screenTime;
    private static final ArrayList<AbstractSurvivorCard> rewardCards = new ArrayList<>();

    public void update() {
        if (screenTimer != 0.0F) {// 504
            screenTimer -= Gdx.graphics.getDeltaTime();// 505
            if (screenTimer < 0.0F) {// 506
                screenTimer = 0.0F;// 507
            }
            screenColor.a = Interpolation.fade.apply(0.0F, 0.5F, 1.0F - screenTimer / screenTime);
        }
        AbstractSurvivorCard hoveredCard = null;
        for (AbstractSurvivorCard rewardCard : rewardCards) {
            rewardCard.update();
            rewardCard.updateHoverLogic();
            if (rewardCard.hb.justHovered) {
                CardCrawlGame.sound.playV("CARD_OBTAIN", 0.4F);
            }

            if (rewardCard.hb.hovered) {
                hoveredCard = rewardCard;
            }
        }

        if (hoveredCard != null && InputHelper.justClickedLeft) {
            hoveredCard.hb.clickStarted = true;
        }

        /*if (hoveredCard != null && CInputActionSet.select.isJustPressed()) {
            hoveredCard.hb.clicked = true;
        }*/

        if (hoveredCard != null && hoveredCard.hb.clicked) {
            hoveredCard.onPickup(SurvivorDungeon.player);
            SurvivorDungeon.currentScreen = SurvivorDungeon.CurrentScreen.NONE;
            SurvivorDungeon.isScreenUp = false;
            SurvivorDungeon.dynamicBanner.hide();
        }
    }
    
    public void open(boolean animated) {
        rewardCards.clear();
        rewardCards.add(new AttackSpeed());
        rewardCards.add(new CritChance());
        rewardCards.add(new MovementSpeed());
        rewardCards.add(new PickupRange());
        SurvivorDungeon.dynamicBanner.appear(TEXT[0]);
        SurvivorDungeon.isScreenUp = true;
        SurvivorDungeon.currentScreen = SurvivorDungeon.CurrentScreen.CHOICE;
        screenColor.a = 0.0f;
        if (animated) {
            screenTime = screenTimer = 1f;
        } else {
            screenColor.a = 0.5f;
        }
    }    

    public void render(SpriteBatch sb) {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float)Settings.HEIGHT);

        for (AbstractSurvivorCard c : rewardCards) {
            c.target_y = CARD_TARGET_Y;
            c.target_x = (float)Settings.WIDTH / 2.0F + ((float) rewardCards.indexOf(c) - (float)(rewardCards.size() - 1) / 2.0F) * (AbstractCard.IMG_WIDTH + PAD_X);
            c.render(sb);
            c.renderCardTip(sb);
        }
    }
}
