package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.DaggerThrowWeapon;
import SpireSurvivors.weapons.FlamethrowerWeapon;
import SpireSurvivors.weapons.LightParticleThrowWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class DefectCharacter extends AbstractSurvivorPlayer {
    public DefectCharacter(AbstractPlayer p) {
        super(p);
        this.pickupRange *= 2f;
        weapons.add(new FlamethrowerWeapon());
    }
}
