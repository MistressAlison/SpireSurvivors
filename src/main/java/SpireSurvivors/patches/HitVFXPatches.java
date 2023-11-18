package SpireSurvivors.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;

public class HitVFXPatches {
    @SpirePatch2(clz = DamageNumberEffect.class, method = "update")
    @SpirePatch2(clz = DamageImpactLineEffect.class, method = "update")
    @SpirePatch2(clz = DamageImpactCurvyEffect.class, method = "update")
    public static class SmallerEffect {
        @SpirePostfixPatch
        public static void plz(AbstractGameEffect __instance, @ByRef float[] ___scale) {
            ___scale[0] *= 0.25f;
        }
    }

    @SpirePatch2(clz = DamageImpactLineEffect.class, method = SpirePatch.CONSTRUCTOR)
    @SpirePatch2(clz = DamageImpactCurvyEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, Color.class, boolean.class})
    public static class SlowerEffect {
        @SpirePostfixPatch
        public static void plz(AbstractGameEffect __instance, @ByRef float[] ___speed) {
            ___speed[0] *= 0.25f;
        }
    }

    @SpirePatch2(clz = DamageImpactCurvyEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {float.class, float.class, Color.class, boolean.class})
    public static class SlowerEffect2 {
        @SpirePostfixPatch
        public static void plz(AbstractGameEffect __instance, @ByRef float[] ___speedStart) {
            ___speedStart[0] *= 0.25f;
        }
    }
}
