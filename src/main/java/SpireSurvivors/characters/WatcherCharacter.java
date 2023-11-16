package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.LightParticleThrowWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class WatcherCharacter extends AbstractSurvivorPlayer {
    public WatcherCharacter(AbstractPlayer p) {
        super(p);
        attackspeedModifier *= 1.2f;
        weapons.add(new LightParticleThrowWeapon());
    }
}
