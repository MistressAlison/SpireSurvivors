package SpireSurvivors.pickups;

import SpireSurvivors.dungeon.SurvivorDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class XPPickup extends AbstractSurvivorInteractable {
    private int amount;
    public XPPickup(int amount, float x, float y) {
        super(ImageMaster.SCROLL_BAR_TRAIN, x, y, 40f, 40f);
        this.amount = amount;
    }

    @Override
    public void onTouch() {
        this.isDone = true;
        SurvivorDungeon.player.gainXP(amount);
    }
}
