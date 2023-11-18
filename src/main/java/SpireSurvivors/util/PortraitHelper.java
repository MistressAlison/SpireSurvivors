
package SpireSurvivors.util;

import basemod.Pair;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.HashMap;

import static com.badlogic.gdx.graphics.GL20.GL_DST_COLOR;
import static com.badlogic.gdx.graphics.GL20.GL_ZERO;

public class PortraitHelper {
    private static class MaskData {
        public String ID;
        public AbstractCard.CardType type;
        public TextureAtlas.AtlasRegion region;

        public MaskData(String ID, AbstractCard.CardType type, TextureAtlas.AtlasRegion region) {
            this.ID = ID;
            this.type = type;
            this.region = region;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MaskData)) {
                return false;
            } else {
                MaskData other = (MaskData) obj;
                return ID.equals(other.ID) && type.equals(other.type) && region.equals(other.region);
            }
        }
    }

    @SpirePatch(clz= AbstractCard.class, method=SpirePatch.CLASS)
    public static class MaskDataField {
        public static SpireField<MaskData> data = new SpireField<>(() -> null);
    }

    private static final Texture attackMask = TextureLoader.getTexture("SpireSurvivorsResources/images/masks/AttackMask.png");
    private static final Texture skillMask = TextureLoader.getTexture("SpireSurvivorsResources/images/masks/SkillMask.png");
    private static final Texture powerMask = TextureLoader.getTexture("SpireSurvivorsResources/images/masks/PowerMask.png");
    private static final int WIDTH = 250;
    private static final int HEIGHT = 190;
    private static final HashMap<MaskData, Pair<TextureAtlas.AtlasRegion, Texture>> hashedTextures = new HashMap<>();

    public static void setMaskedPortrait(AbstractCard card) {
        setMaskedPortrait(card, null);
    }

    public static void setMaskedPortrait(AbstractCard card, TextureAtlas.AtlasRegion r) {
        MaskData key = new MaskData(card.cardID, card.type, r);
        MaskDataField.data.set(card, key);
        if (hashedTextures.containsKey(key)) {
            card.portrait = hashedTextures.get(key).getKey();
        } else {
            Texture temp = makeMaskedTexture(card, r, 2);
            card.portrait = new TextureAtlas.AtlasRegion(makeMaskedTexture(card, r, 1), 0, 0, WIDTH, HEIGHT);
            hashedTextures.put(key, new Pair<>(card.portrait, temp));
        }
    }

    public static Texture makeMaskedTexture(AbstractCard card, TextureAtlas.AtlasRegion r, int multi) {
        int width = WIDTH * multi;
        int height = HEIGHT * multi;

        AbstractCard baseCard = CardLibrary.getCard(card.cardID).makeCopy();
        TextureAtlas.AtlasRegion t = baseCard.portrait;
        FrameBuffer fb = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        SpriteBatch sb = new SpriteBatch();
        OrthographicCamera og = new OrthographicCamera(width, height);
        t.flip(false, true);
        if (r != null) {
            r.flip(false, true);
        }
        if (baseCard.type == AbstractCard.CardType.ATTACK) {
            if (card.type == AbstractCard.CardType.POWER) {
                //Attack to Power
                og.zoom = 0.95f;
                og.translate(-3, 0);
            } else {
                //Attack to Skill, Status, Curse
                og.zoom = 0.9f;
                og.translate(0, -10);
            }
        } else if (baseCard.type == AbstractCard.CardType.POWER) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                //Power to Attack
                og.zoom = 0.9f;
                og.translate(0, -10);
            } else {
                //Power to Skill, Status, Curse
                og.zoom = 0.825f;
                og.translate(-1, -18);
            }
        } else {
            if (card.type == AbstractCard.CardType.POWER) {
                //Skill, Status, Curse to Power
                og.zoom = 0.95f;
                og.translate(-3, 0);
            }
            //Skill, Status, Curse to Attack is free
            og.translate(-3, 0);
        }

        og.update();
        sb.setProjectionMatrix(og.combined);

        ImageHelper.beginBuffer(fb);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        fb.begin();
        sb.begin();

        sb.setColor(Color.WHITE.cpy());
        sb.draw(t, -width/2f, -height/2f, -width/2f, -height/2f, width, height, 1, 1, 0);
        //sb.draw(t, -t.packedWidth/2f*multi, -t.packedHeight/2f*multi, -t.packedWidth/2f*multi, -t.packedHeight/2f*multi, t.packedWidth*multi, t.packedHeight*multi, 1, 1, 0);
        if (r != null) {
            sb.draw(r, -r.packedWidth/2f*multi, -r.packedHeight/2f*multi);
        }
        sb.setBlendFunction(GL_DST_COLOR, GL_ZERO);
        sb.setProjectionMatrix(new OrthographicCamera(width, height).combined);

        Texture mask = skillMask;
        if (card.type == AbstractCard.CardType.ATTACK) {
            mask = attackMask;
        } else if (card.type == AbstractCard.CardType.POWER) {
            mask = powerMask;
        }
        sb.draw(mask, -width/2f, -height/2f, -width/2f, -height/2f, width, height, 1, 1, 0, 0, 0, mask.getWidth(), mask.getHeight(), false, true);

        sb.end();
        fb.end();
        t.flip(false, true);
        if (r != null) {
            r.flip(false, true);
        }
        return fb.getColorBufferTexture();
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "loadPortraitImg")
    public static class FixSCVHopefully {
        @SpirePostfixPatch
        public static void dontExplode(SingleCardViewPopup __instance, @ByRef Texture[] ___portraitImg, AbstractCard ___card) {
            MaskData data = MaskDataField.data.get(___card);
            if (data != null) {
                ___portraitImg[0] = TextureScaler.rescale(hashedTextures.get(data).getKey(), 2f);
            }
        }
    }

    @SpirePatch2(clz = CustomCard.class, method = "getPortraitImage", paramtypez = {})
    public static class FixCustomCardHopefully {
        @SpirePostfixPatch
        public static void plz(CustomCard __instance, @ByRef Texture[] __result) {
            MaskData data = MaskDataField.data.get(__instance);
            if (data != null) {
                __result[0] = makeMaskedTexture(__instance, data.region, 2);
            }
        }
    }
}
