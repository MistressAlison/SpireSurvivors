package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.DaggerThrowWeapon;
import SpireSurvivors.weapons.StrikeWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class SilentCharacter extends AbstractSurvivorPlayer {
    public SilentCharacter(AbstractPlayer p) {
        super(p);
        weapons.add(new DaggerThrowWeapon());
    }
}
