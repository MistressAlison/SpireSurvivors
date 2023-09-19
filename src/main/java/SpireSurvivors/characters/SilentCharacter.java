package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.DaggerThrowWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class SilentCharacter extends AbstractSurvivorPlayer {
    public SilentCharacter(AbstractPlayer p) {
        super(p);
        speed*=1.2;
        weapons.add(new DaggerThrowWeapon());
    }
}
