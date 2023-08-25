package SpireSurvivors.dungeon;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.characters.BasicCharacter;
import SpireSurvivors.entity.AbstractSurvivorMonster;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.monsters.BasicMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class SurvivorDungeon {
    public enum CurrentScreen {
        NONE,
        SETTINGS,
        CHOICE,
        DEATH
    }
    public static final TextureAtlas.AtlasRegion BACKGROUND = new TextureAtlas(Gdx.files.internal("bottomScene/scene.atlas")).findRegion("event");
    public static final InputAction UP = new InputAction(Input.Keys.W);
    public static final InputAction LEFT = new InputAction(Input.Keys.A);
    public static final InputAction DOWN = new InputAction(Input.Keys.S);
    public static final InputAction RIGHT = new InputAction(Input.Keys.D);
    public static final float MX = Settings.WIDTH * -0.75F;
    public static final float MY = -AbstractDungeon.floorY;
    public static AbstractSurvivorPlayer player;
    public static ArrayList<AbstractSurvivorMonster> monsters = new ArrayList<>();
    public static ArrayList<AbstractGameEffect> effects = new ArrayList<>();
    public static ArrayList<AbstractGameEffect> effectsQueue = new ArrayList<>();
    public static float waveTimer;
    public static int waveCounter;
    public static float worldX, worldY;

    public SurvivorDungeon(AbstractPlayer player) {
        SurvivorDungeon.player = SpireSurvivorsMod.registeredCharacters.getOrDefault(player.chosenClass, new BasicCharacter(player));
        AbstractDungeon.miscRng = new Random(Settings.seed);
        CardCrawlGame.music.changeBGM(Exordium.ID);
        Settings.hideCombatElements = false;
        clear();
        CardCrawlGame.fadeIn(0.5f);
    }

    public void update() {
        player.update();
        updateInput();
        monsters.removeIf(m -> m.monster.isDead);
        for (AbstractSurvivorMonster m : monsters) {
            m.update();
        }
        waveTimer -= Gdx.graphics.getDeltaTime();
        if (waveTimer <= 0f) {
            waveTimer = 4f;
            waveCounter++;
            spawnMonsters();
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
        sb.draw(BACKGROUND, 0, 0, Settings.WIDTH, Settings.HEIGHT);
        for (AbstractSurvivorMonster m : monsters) {
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
            dir.scl(player.speed);
            //worldX -= dir.x;
            //worldY -= dir.y;
            transformWorld(dir);
        }
    }

    public void transformWorld(Vector2 dir) {
        for (AbstractSurvivorMonster m : monsters) {
            m.move(-dir.x, -dir.y);
        }
    }

    public void spawnMonsters() {
        pepperSpawn(waveCounter + 2);
    }

    public void pepperSpawn(int amount) {
        for (int i = 0 ; i < amount ; i++) {
            Vector2 spawn = new Vector2(0, 1);
            spawn.rotate(MathUtils.random(360));
            spawn.scl(Settings.WIDTH/2f);
            monsters.add(new BasicMonster(new LouseNormal(MX+spawn.x, MY+spawn.y)));
        }
    }

    public void circleSpawn() {

    }

    public void clear() {
        waveTimer = 0f;
        waveCounter = 0;
        monsters.clear();
        effects.clear();
        effectsQueue.clear();
    }
}
