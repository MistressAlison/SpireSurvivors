package SpireSurvivors.pickups;

import SpireSurvivors.dungeon.SurvivorDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MagnetPickup extends AbstractSurvivorInteractable {
    public MagnetPickup(float x, float y) {
        super(AbstractPower.atlas.findRegion("128/magnet"), x, y, 40f, 40f);
    }

    @Override
    public void onTouch() {
        for (AbstractSurvivorInteractable i : SurvivorDungeon.pickups) {
            if (i instanceof XPPickup) {
                ((XPPickup) i).magnetGrab = true;
            }
        }
        this.isDone = true;
    }
}
