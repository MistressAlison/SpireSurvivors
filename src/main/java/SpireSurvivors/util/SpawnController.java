package SpireSurvivors.util;

import SpireSurvivors.dungeon.SurvivorDungeon;
import SpireSurvivors.monsters.BasicMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;

public class SpawnController {
    public static final float MX = (Settings.WIDTH * -0.75F + Settings.WIDTH/2f)/Settings.xScale;
    public static final float MY = (-AbstractDungeon.floorY + Settings.HEIGHT/2f)/Settings.yScale;
    public float waveTimer;
    public int waveCounter;

    public void update() {
        waveTimer -= Gdx.graphics.getDeltaTime();
        if (waveTimer <= 0f) {
            waveTimer = 4f;
            waveCounter++;
            spawnMonsters();
        }
    }

    public void spawnMonsters() {
        pepperSpawn(waveCounter + 2);
    }

    public void pepperSpawn(int amount) {
        for (int i = 0 ; i < amount ; i++) {
            Vector2 spawn = new Vector2(0, 1);
            spawn.rotate(MathUtils.random(360));
            spawn.scl(1920);
            SurvivorDungeon.monsters.add(new BasicMonster(new LouseNormal(MX+spawn.x, MY+spawn.y)));
        }
    }

    public void circleSpawn() {

    }
}
