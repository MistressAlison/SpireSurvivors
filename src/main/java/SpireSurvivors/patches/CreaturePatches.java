package SpireSurvivors.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CreaturePatches {
    @SpirePatch2(clz = AbstractCreature.class, method = "loadAnimation")
    public static class SmallerEntities {
        @SpirePrefixPatch
        public static void shrink(@ByRef float[] scale) {
            if (CardCrawlGamePatches.survivorGame != null || CardCrawlGamePatches.loadSurvivorMode) {
                scale[0] *= 2;
            }
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = SpirePatch.CONSTRUCTOR)
    public static class FixPrivateFields {

    }
}
