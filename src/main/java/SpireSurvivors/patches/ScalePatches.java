package SpireSurvivors.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ScalePatches {
    @SpirePatch2(clz = AbstractCreature.class, method = "loadAnimation")
    public static class SmallerEntities {
        @SpirePrefixPatch
        public static void shrink(@ByRef float[] scale) {
            if (CardCrawlGamePatches.survivorGame != null || CardCrawlGamePatches.loadSurvivorMode) {
                scale[0] *= 2;
            }
        }
    }
}
