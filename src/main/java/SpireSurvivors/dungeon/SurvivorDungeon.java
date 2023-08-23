package SpireSurvivors.dungeon;

import SpireSurvivors.entity.SurvivorMonster;
import SpireSurvivors.entity.SurvivorPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class SurvivorDungeon {
    public static final InputAction UP = new InputAction(Input.Keys.W);
    public static final InputAction LEFT = new InputAction(Input.Keys.A);
    public static final InputAction RIGHT = new InputAction(Input.Keys.S);
    public static final InputAction DOWN = new InputAction(Input.Keys.D);
    public static SurvivorPlayer player;
    public static ArrayList<SurvivorMonster> monsters = new ArrayList<>();
    public static float waveTimer;
    public static float worldX, worldY;

    public SurvivorDungeon(AbstractPlayer player) {
        SurvivorDungeon.player = new SurvivorPlayer(player);
        AbstractDungeon.miscRng = new Random(Settings.seed);
        CardCrawlGame.music.changeBGM(Exordium.ID);
    }

    public void update() {
        player.update();
        updateInput();
        for (SurvivorMonster m : monsters) {
            m.update();
        }
        waveTimer -= Gdx.graphics.getDeltaTime();
        if (waveTimer <= 0f) {
            waveTimer = 4f;
            monsters.add(new SurvivorMonster(new LouseNormal(400f, 800f)));
        }
    }

    public void render(SpriteBatch sb) {
        player.render(sb);
        for (SurvivorMonster m : monsters) {
            m.render(sb);
        }
    }

    public void updateInput() {

    }
}
