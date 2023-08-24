package SpireSurvivors.dungeon;

import SpireSurvivors.entity.SurvivorMonster;
import SpireSurvivors.entity.SurvivorPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SurvivorDungeon {
    public static final InputAction UP = new InputAction(Input.Keys.W);
    public static final InputAction LEFT = new InputAction(Input.Keys.A);
    public static final InputAction DOWN = new InputAction(Input.Keys.S);
    public static final InputAction RIGHT = new InputAction(Input.Keys.D);
    public static SurvivorPlayer player;
    public static ArrayList<SurvivorMonster> monsters = new ArrayList<>();
    public static ArrayList<AbstractGameEffect> effects = new ArrayList<>();
    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();
    public static float waveTimer;
    public static float worldX, worldY;

    public SurvivorDungeon(AbstractPlayer player) {
        SurvivorDungeon.player = new SurvivorPlayer(player);
        AbstractDungeon.miscRng = new Random(Settings.seed);
        CardCrawlGame.music.changeBGM(Exordium.ID);
        Settings.hideCombatElements = false;
    }

    public void update() {
        player.update();
        updateInput();
        monsters.removeIf(m -> m.monster.isDead);
        for (SurvivorMonster m : monsters) {
            m.update();
        }
        waveTimer -= Gdx.graphics.getDeltaTime();
        if (waveTimer <= 0f) {
            waveTimer = 4f;
            monsters.add(new SurvivorMonster(new LouseNormal(400f, 800f)));
        }
        effects.addAll(effectsQueue);
        effectsQueue.clear();
        effects.addAll(AbstractDungeon.effectsQueue);
        AbstractDungeon.effectsQueue.clear();
        for (AbstractGameEffect e : effects) {
            e.update();
        }
        effects.removeIf(e -> e.isDone);

    }

    public void render(SpriteBatch sb) {
        for (SurvivorMonster m : monsters) {
            m.render(sb);
        }
        player.render(sb);
        for (AbstractGameEffect e : effects) {
            e.render(sb);
        }
    }

    public void updateInput() {
        Vector2 dir = new Vector2();
        if (UP.isPressed()) {
            dir.y += 1;
        }
        if (DOWN.isPressed()) {
            dir.y -= 1;
        }
        if (LEFT.isPressed()) {
            dir.x -= 1;
        }
        if (RIGHT.isPressed()) {
            dir.x += 1;
        }
        if (dir.len() != 0) {
            dir.nor();
            dir.scl(player.movementSpeed);
            //worldX -= dir.x;
            //worldY -= dir.y;
            transformWorld(dir);
        }
    }

    public void transformWorld(Vector2 dir) {
        for (SurvivorMonster m : monsters) {
            m.move(-dir.x, -dir.y);
        }
    }
}
