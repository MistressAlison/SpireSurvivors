package SpireSurvivors.patches;

import SpireSurvivors.SpireSurvivorsMod;
import SpireSurvivors.dungeon.SurvivorDungeon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.DrawMaster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.apache.logging.log4j.Logger;

public class CardCrawlGamePatches {
    public static final String SURVIVOR_MODE = SpireSurvivorsMod.makeID("SurvivorMode");
    public static boolean loadSurvivorMode;
    public static SurvivorDungeon survivorGame;

    public static class Enums {
        @SpireEnum
        public static CardCrawlGame.GameMode SURVIVOR_GAMEPLAY;
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "update")
    public static class LoadSurvivorMode {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(CardCrawlGame __instance) {
            if (CardCrawlGame.mode == CardCrawlGame.GameMode.GAMEPLAY && loadSurvivorMode) {
                CardCrawlGame.mode = Enums.SURVIVOR_GAMEPLAY;
                if (CardCrawlGame.dungeonTransitionScreen != null) {
                    CardCrawlGame.dungeonTransitionScreen.isComplete = true;
                    CardCrawlGame.dungeonTransitionScreen = null;
                }
                survivorGame = new SurvivorDungeon(CardCrawlGame.characterManager.recreateCharacter(CardCrawlGame.chosenCharacter));
                loadSurvivorMode = false;
            } else if (CardCrawlGame.mode == Enums.SURVIVOR_GAMEPLAY) {
                if (survivorGame != null) {
                    survivorGame.update();
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(CardCrawlGame.class, "updateDebugSwitch");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "render")
    public static class RenderCustom {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(SpriteBatch ___sb) {
            if (CardCrawlGame.mode == Enums.SURVIVOR_GAMEPLAY) {
                if (CardCrawlGame.dungeonTransitionScreen != null) {
                    CardCrawlGame.dungeonTransitionScreen.render(___sb);
                } else if (survivorGame != null) {
                    survivorGame.render(___sb);
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(DrawMaster.class, "draw");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "update")
    public static class StopSpammingLogs {
        static int hits = 0;
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(Logger.class.getName()) && m.getMethodName().equals("info")) {
                        hits++;
                        if (hits == 3) {
                            m.replace("if(SpireSurvivors.patches.CardCrawlGamePatches.shouldLog()){$proceed($$);}");
                        }
                    }
                }
            };
        }
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "render")
    public static class StopSpammingLogs2 {
        static int hits = 0;
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(Logger.class.getName()) && m.getMethodName().equals("info")) {
                        hits++;
                        if (hits == 1) {
                            m.replace("if(SpireSurvivors.patches.CardCrawlGamePatches.shouldLog()){$proceed($$);}");
                        }
                    }
                }
            };
        }
    }

    public static boolean shouldLog() {
        return CardCrawlGame.mode != Enums.SURVIVOR_GAMEPLAY;
    }
}
