package SpireSurvivors.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class PolygonHelper {
    public static final ShapeRenderer sr = new ShapeRenderer();

    public static Polygon fromPosition(float x, float y, float width, float height) {
        Polygon p = new Polygon(new float[]{0,0, width,0, width,height, 0,height});
        p.setOrigin(width/2f, height/2f);
        p.setPosition(x-width/2f, y-height/2f);
        return p;
    }

    public static Polygon fromPosition(float x, float y, float width, float height, float rotation) {
        Polygon p = new Polygon(new float[]{0,0, width,0, width,height, 0,height});
        p.setOrigin(width/2f, height/2f);
        p.rotate(rotation);
        p.setPosition(x-width/2f, y-height/2f);
        return p;
    }

    public static Polygon fromPosition(float x, float y, float width, float height, float rotation, float dx, float dy) {
        Polygon p = new Polygon(new float[]{0,0, width,0, width,height, 0,height});
        p.setOrigin(width/2f, height/2f);
        p.rotate(rotation);
        Vector2 dir = new Vector2(dx, dy).rotate(rotation);
        p.setPosition(x-width/2f + dir.x, y-height/2f + dir.y);
        return p;
    }

    public static Polygon fromHitbox(Hitbox hb) {
        Polygon p = new Polygon(new float[]{0,0, hb.width,0, hb.width,hb.height, 0,hb.height});
        p.setOrigin(hb.cX, hb.cY);
        p.setPosition(hb.x, hb.y);
        return p;
    }

    public static boolean collides(Polygon a, Polygon b) {
        return Intersector.overlapConvexPolygons(a, b);
    }

    public static void renderPolygon(Polygon g, SpriteBatch sb) {
        sb.end();
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.polygon(g.getTransformedVertices());
        sr.end();
        sb.begin();
    }
}
