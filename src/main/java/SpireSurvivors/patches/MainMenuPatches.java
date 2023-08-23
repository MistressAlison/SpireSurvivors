package SpireSurvivors.patches;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.SurvivorSelectScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import javassist.CtBehavior;

public class MainMenuPatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SpireSurvivorsMod.makeID("MainMenu"));
    public static final String[] TEXT = uiStrings.TEXT;

    public static class Enums {
        @SpireEnum
        public static MenuButton.ClickResult SURVIVORS_BUTTON;
        @SpireEnum
        public static MainMenuScreen.CurScreen LOADOUT_VIEW;
        @SpireEnum
        public static MenuPanelScreen.PanelScreen SURVIVORS_PANEL;
        @SpireEnum
        public static MainMenuPanelButton.PanelClickResult PLAY_SURVIVORS;
    }

    @SpirePatch(clz = MainMenuScreen.class, method = SpirePatch.CLASS)
    public static class SurvivorScreenField {
        public static SpireField<SurvivorSelectScreen> survivorScreen = new SpireField<>(() -> null);
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "setMainMenuButtons")
    public static class ButtonAdderPatch {
        @SpireInsertPatch(locator= ButtonLocator.class, localvars={"index"})
        public static void setMainMenuButtons(MainMenuScreen __instance, @ByRef int[] index) {
            __instance.buttons.add(new MenuButton(Enums.SURVIVORS_BUTTON, index[0]));
            index[0]++;
        }

        private static class ButtonLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CharacterManager.class, "anySaveFileExists");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = MenuButton.class, method = "setLabel")
    public static class SetText {
        @SpirePostfixPatch
        public static void useLocalizedText(MenuButton __instance, @ByRef String[] ___label) {
            if (__instance.result == Enums.SURVIVORS_BUTTON) {
                ___label[0] = TEXT[0];
            }
        }
    }

    @SpirePatch2(clz = MenuButton.class, method = "buttonEffect")
    public static class OnClickButton {
        @SpirePostfixPatch
        public static void useLocalizedText(MenuButton __instance) {
            if (__instance.result == Enums.SURVIVORS_BUTTON) {
                CardCrawlGame.mainMenuScreen.panelScreen.open(Enums.SURVIVORS_PANEL);
            }
        }
    }

    @SpirePatch(clz = MenuPanelScreen.class, method = "initializePanels")
    public static class InitPanels {
        @SpirePostfixPatch
        public static void init(MenuPanelScreen __instance, MenuPanelScreen.PanelScreen ___screen, float ___PANEL_Y) {
            if (___screen == Enums.SURVIVORS_PANEL) {
                __instance.panels.add(new MainMenuPanelButton(
                        Enums.PLAY_SURVIVORS,
                        MainMenuPanelButton.PanelColor.BLUE,
                        Settings.WIDTH / 2f - 225f * Settings.scale,
                        ___PANEL_Y
                ));
                __instance.panels.add(new MainMenuPanelButton(
                        MainMenuPanelButton.PanelClickResult.PLAY_CUSTOM,
                        MainMenuPanelButton.PanelColor.RED,
                        Settings.WIDTH / 2f + 225f * Settings.scale,
                        ___PANEL_Y
                ));
            }
        }
    }

    @SpirePatch2(clz = MainMenuPanelButton.class, method = "setLabel")
    public static class SetTextAndImage {
        @SpirePostfixPatch
        public static void plz(MainMenuPanelButton.PanelClickResult ___result, @ByRef String[] ___header, @ByRef String[] ___description, @ByRef Texture[] ___portraitImg) {
            if (___result == Enums.PLAY_SURVIVORS) {
                ___header[0] = TEXT[1];
                ___description[0] = TEXT[2];
                ___portraitImg[0] = ImageMaster.P_STANDARD;
            }
        }
    }

    @SpirePatch2(clz = MainMenuPanelButton.class, method = "buttonEffect")
    public static class DoButtonEffect {
        @SpirePostfixPatch
        public static void plz(MainMenuPanelButton.PanelClickResult ___result) {
            if (___result == Enums.PLAY_SURVIVORS) {
                SurvivorScreenField.survivorScreen.get(CardCrawlGame.mainMenuScreen).open();
            }
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
    private static class AddNewScreenToSpireField {
        @SpirePostfixPatch()
        public static void screenTime(MainMenuScreen __instance) {
            SurvivorScreenField.survivorScreen.set(__instance, new SurvivorSelectScreen());
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "update")
    public static class UpdateLoadoutScreen {
        @SpireInsertPatch(locator= UpdateLocator.class)
        public static void updateTime(MainMenuScreen __instance) {
            if (__instance.screen == Enums.LOADOUT_VIEW) {
                SurvivorScreenField.survivorScreen.get(__instance).update();
            }
        }

        private static class UpdateLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SaveSlotScreen.class, "update");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = MainMenuScreen.class, method = "render")
    public static class RenderLoadoutScreen {
        @SpireInsertPatch(locator= RenderLocator.class)
        public static void renderTime(MainMenuScreen __instance, SpriteBatch sb) {
            if (__instance.screen == Enums.LOADOUT_VIEW) {
                SurvivorScreenField.survivorScreen.get(__instance).render(sb);
            }
        }

        private static class RenderLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SaveSlotScreen.class, "render");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
