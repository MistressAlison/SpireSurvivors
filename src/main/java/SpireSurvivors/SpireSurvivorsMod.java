package SpireSurvivors;

import SpireSurvivors.cards.abstracts.AbstractStatCard;
import SpireSurvivors.characters.DefectCharacter;
import SpireSurvivors.characters.IroncladCharacter;
import SpireSurvivors.characters.SilentCharacter;
import SpireSurvivors.characters.WatcherCharacter;
import SpireSurvivors.entity.AbstractSurvivorPlayer;
import SpireSurvivors.relics.abstracts.AbstractSurvivorRelic;
import SpireSurvivors.util.TextureLoader;
import SpireSurvivors.weapons.abstracts.AbstractSurvivorWeapon;
import basemod.*;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostRenderSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.Function;

@SpireInitializer
public class SpireSurvivorsMod implements EditStringsSubscriber, PostInitializeSubscriber, PostRenderSubscriber, PostUpdateSubscriber {
    public static final Logger logger = LogManager.getLogger(SpireSurvivorsMod.class.getName());
    private static String modID;
    public static SpireConfig LOConfig;
    public static String FILE_NAME = "SpireSurvivorsConfig";

    public static final String ENABLE_MOD = "enableMod";
    public static boolean modEnabled = true;

    public static final String TORCH_MODE = "torchMode";
    public static boolean torchMode = false;

    public static final String MOUSE_RADIUS = "mouseRadium";
    public static int mouseRadius = 360;

    public static final String TORCH_MODE_DECAY = "torchModeDecay";
    public static int torchModeDecay = 5;

    public static final String AMBIENT_LIGHT = "ambientLight";
    public static int ambientLight = 0;

    public static final String COLORFUL_MAP = "colorfulMap";
    public static boolean colorfulMap = false;

    public static final String GLOWING_MAP = "glowingMap";
    public static boolean glowingMap = true;

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;

    private static final String MODNAME = "Spire Survivors";
    private static final String AUTHOR = "Mistress Autumn";
    private static final String DESCRIPTION = "This is a bad idea.";
    public static final String BADGE_IMAGE = "SpireSurvivorsResources/images/Badge.png";

    public static final ArrayList<AbstractGameEffect> managedEffects = new ArrayList<>();
    public static final HashMap<AbstractPlayer.PlayerClass, Function<AbstractPlayer, AbstractSurvivorPlayer>> registeredCharacters = new HashMap<>();

    public static final ArrayList<AbstractSurvivorWeapon> weapons = new ArrayList<>();
    public static final ArrayList<AbstractSurvivorRelic> relics = new ArrayList<>();
    public static final ArrayList<AbstractStatCard> stats = new ArrayList<>();

    // This doesn't work in SurvivorDungeon so I guess it's here now
    public static boolean seenMovementTutorial = false;

    public SpireSurvivorsMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("SpireSurvivors");
        logger.info("Done subscribing");
        logger.info("Adding mod settings");
        Properties LODefaultSettings = new Properties();
        LODefaultSettings.setProperty(ENABLE_MOD, Boolean.toString(modEnabled));
        LODefaultSettings.setProperty(TORCH_MODE, Boolean.toString(torchMode));
        LODefaultSettings.setProperty(MOUSE_RADIUS, String.valueOf(mouseRadius));
        LODefaultSettings.setProperty(TORCH_MODE_DECAY, String.valueOf(torchModeDecay));
        LODefaultSettings.setProperty(AMBIENT_LIGHT, String.valueOf(ambientLight));
        LODefaultSettings.setProperty(COLORFUL_MAP, Boolean.toString(colorfulMap));
        LODefaultSettings.setProperty(GLOWING_MAP, Boolean.toString(glowingMap));
        try {
            LOConfig = new SpireConfig(modID, FILE_NAME, LODefaultSettings);
            modEnabled = LOConfig.getBool(ENABLE_MOD);
            torchMode = LOConfig.getBool(TORCH_MODE);
            mouseRadius = LOConfig.getInt(MOUSE_RADIUS);
            torchModeDecay = LOConfig.getInt(TORCH_MODE_DECAY);
            ambientLight = LOConfig.getInt(AMBIENT_LIGHT);
            colorfulMap = LOConfig.getBool(COLORFUL_MAP);
            glowingMap = LOConfig.getBool(GLOWING_MAP);
        } catch (IOException e) {
            logger.error("Spire Survivors SpireConfig initialization failed:");
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }

    public static void setModID(String ID) {
        modID = ID;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        SpireSurvivorsMod spireSurvivorsMod = new SpireSurvivorsMod();
    }

    public void receivePostInitialize() {
        //Load characters
        registerCharacter(AbstractPlayer.PlayerClass.IRONCLAD, IroncladCharacter::new);
        registerCharacter(AbstractPlayer.PlayerClass.THE_SILENT, SilentCharacter::new);
        registerCharacter(AbstractPlayer.PlayerClass.DEFECT, DefectCharacter::new);
        registerCharacter(AbstractPlayer.PlayerClass.WATCHER, WatcherCharacter::new);
        
        logger.info("Loading badge image and mod options");
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
        TEXT = uiStrings.TEXT;
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        float currentYposition = 740.0F;
        float sliderOffset = 50.0F + FontHelper.getWidth(FontHelper.charDescFont, TEXT[3], 1.0F / Settings.scale);
        float spacingY = 55.0F;

        ModLabeledToggleButton enableModsButton = new ModLabeledToggleButton(TEXT[0], 360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, LOConfig.getBool(ENABLE_MOD), settingsPanel, label -> {},button -> {
            LOConfig.setBool(ENABLE_MOD, button.enabled);
            modEnabled = button.enabled;
            if (modEnabled) {
                CardCrawlGame.sound.playA("ATTACK_FIRE", 0.4F);
            } else {
                CardCrawlGame.sound.play("SCENE_TORCH_EXTINGUISH");
            }
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        ModLabeledToggleButton glowingMapButton = new ModLabeledToggleButton(TEXT[6], 360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, LOConfig.getBool(GLOWING_MAP), settingsPanel, label -> {},button -> {
            LOConfig.setBool(GLOWING_MAP, button.enabled);
            glowingMap = button.enabled;
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        ModLabeledToggleButton colorfulButton = new ModLabeledToggleButton(TEXT[5], 360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, LOConfig.getBool(COLORFUL_MAP), settingsPanel, label -> {},button -> {
            LOConfig.setBool(COLORFUL_MAP, button.enabled);
            colorfulMap = button.enabled;
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;


        ModLabeledToggleButton torchModeButton = new ModLabeledToggleButton(TEXT[1], 360.0F, currentYposition - 10.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, LOConfig.getBool(TORCH_MODE), settingsPanel, label -> {},button -> {
            LOConfig.setBool(TORCH_MODE, button.enabled);
            torchMode = button.enabled;
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        ModLabel radLabel = new ModLabel(TEXT[2], 400.0F, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, modLabel -> {});
        ModMinMaxSlider radSlider = new ModMinMaxSlider("", 400.0F + sliderOffset, currentYposition + 7.0F, 0.0F, 540.0F, LOConfig.getInt(MOUSE_RADIUS), "%.0f", settingsPanel, slider -> {
            LOConfig.setInt(MOUSE_RADIUS, Math.round(slider.getValue()));
            mouseRadius = Math.round(slider.getValue());
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        ModLabel decayLabel = new ModLabel(TEXT[3], 400.0F, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, modLabel -> {});
        ModMinMaxSlider decaySlider = new ModMinMaxSlider("", 400.0F + sliderOffset, currentYposition + 7.0F, 0.0F, 10.0F, LOConfig.getInt(TORCH_MODE_DECAY), "%.0f", settingsPanel, slider -> {
            LOConfig.setInt(TORCH_MODE_DECAY, Math.round(slider.getValue()));
            torchModeDecay = Math.round(slider.getValue());
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        ModLabel ambientLabel = new ModLabel(TEXT[4], 400.0F, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, modLabel -> {});
        ModMinMaxSlider ambientSlider = new ModMinMaxSlider("", 400.0F + sliderOffset, currentYposition + 7.0F, 0.0F, 100.0F, LOConfig.getInt(AMBIENT_LIGHT), "%.0f", settingsPanel, slider -> {
            LOConfig.setInt(AMBIENT_LIGHT, Math.round(slider.getValue()));
            ambientLight = Math.round(slider.getValue());
            try {
                LOConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentYposition -= spacingY;

        settingsPanel.addUIElement(enableModsButton);
        settingsPanel.addUIElement(glowingMapButton);
        settingsPanel.addUIElement(colorfulButton);
        settingsPanel.addUIElement(torchModeButton);
        settingsPanel.addUIElement(radLabel);
        settingsPanel.addUIElement(radSlider);
        settingsPanel.addUIElement(decayLabel);
        settingsPanel.addUIElement(decaySlider);
        settingsPanel.addUIElement(ambientLabel);
        settingsPanel.addUIElement(ambientSlider);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

        new AutoAdd(modID).packageFilter("SpireSurvivors.weapons").any(AbstractSurvivorWeapon.class, (info, weapon) -> weapons.add(weapon));
        new AutoAdd(modID).packageFilter("SpireSurvivors.relics").any(AbstractSurvivorRelic.class, (info, relic) -> relics.add(relic));
        new AutoAdd(modID).packageFilter("SpireSurvivors.cards.statCards").any(AbstractStatCard.class, (into, stat) -> stats.add(stat));
    }

    private String loadLocalizationIfAvailable(String fileName) {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + Settings.language.toString().toLowerCase() + "/" + fileName).exists()) {
            logger.info("Language: " + Settings.language.toString().toLowerCase() + ", not currently supported for " + fileName + ".");
            return "eng/" + fileName;
        }
        logger.info("Loaded Language: " + Settings.language.toString().toLowerCase() + ", for " + fileName + ".");
        return Settings.language.toString().toLowerCase() + "/" + fileName;
    }

    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/" + loadLocalizationIfAvailable("CardStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Resources/localization/" + loadLocalizationIfAvailable("UIStrings.json"));
        logger.info("Done editing strings");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostRender(SpriteBatch sb) {
        for (AbstractGameEffect e : managedEffects) {
            e.render(sb);
        }
    }

    @Override
    public void receivePostUpdate() {
        for (AbstractGameEffect e : managedEffects) {
            e.update();
        }
        managedEffects.removeIf(e -> e.isDone);
    }

    public static void registerCharacter(AbstractPlayer.PlayerClass pc, Function<AbstractPlayer, AbstractSurvivorPlayer> getter) {
        registeredCharacters.put(pc, getter);
    }
}
