package SpireSurvivors.characters;

import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.weapons.FlamethrowerWeapon;
import SpireSurvivors.weapons.LightParticleThrowWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class WatcherCharacter extends AbstractSurvivorPlayer {
    public WatcherCharacter(AbstractPlayer p) {
        super(p);
        attackspeedModifier *= 0.8f;
        weapons.add(new LightParticleThrowWeapon());
    }
}
