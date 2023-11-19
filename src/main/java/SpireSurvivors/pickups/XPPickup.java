package SpireSurvivors.pickups;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class XPPickup extends AbstractSurvivorInteractable {
    private int amount;
    public boolean magnetGrab;
    public XPPickup(int amount, float x, float y) {
        super(ImageMaster.SCROLL_BAR_TRAIN, x, y, 40f, 40f);
        this.amount = amount;
    }

    @Override
    public void update() {
        super.update();
        if (magnetGrab) {
            Vector2 dist = new Vector2(SurvivorDungeon.player.basePlayer.hb.cX - hb.cX, SurvivorDungeon.player.basePlayer.hb.cY - hb.cY);
            dist.nor();
            dist.scl(25f * Settings.scale);
            move(dist.x, dist.y);
        }
    }

    @Override
    public void onTouch() {
        this.isDone = true;
        SurvivorDungeon.player.gainXP(amount);
    }
}
