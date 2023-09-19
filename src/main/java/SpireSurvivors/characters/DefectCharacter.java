package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.LightningSparkWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class DefectCharacter extends AbstractSurvivorPlayer {
    public DefectCharacter(AbstractPlayer p) {
        super(p);
        this.pickupRange *= 2f;
        weapons.add(new LightningSparkWeapon());
    }
}
