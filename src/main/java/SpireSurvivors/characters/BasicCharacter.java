package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.StrikeWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class BasicCharacter extends AbstractSurvivorPlayer {
    public BasicCharacter(AbstractPlayer p) {
        super(p);
        weapons.add(new StrikeWeapon());
    }
}
