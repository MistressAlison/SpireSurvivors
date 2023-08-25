package SpireSurvivors.monsters;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.entity.AbstractSurvivorMonster;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BasicMonster extends AbstractSurvivorMonster {
    public BasicMonster(AbstractMonster m) {
        super(m, 10, 3f);
    }

    @Override
    public void movementUpdate() {
        Vector2 dir = new Vector2(SurvivorDungeon.player.basePlayer.hb.cX - monster.hb.cX, SurvivorDungeon.player.basePlayer.hb.cY - monster.hb.cY);
        dir.nor();
        dir.scl(speed);
        move(dir.x, dir.y);
    }
}
