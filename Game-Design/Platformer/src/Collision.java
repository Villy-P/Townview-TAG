import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Collision {
    public CollisionsType thing;
    public CollisionsType other;
    public CollisionsPosition position;
    public BiConsumer<Thing, Thing> onCollide;
    public boolean preventMove = true;

    public Collision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide) {
        this.thing = thing;
        this.other = other;
        this.position = position;
        this.onCollide = onCollide;
    }

    public Collision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide, boolean preventMove) {
        this.thing = thing;
        this.other = other;
        this.position = position;
        this.onCollide = onCollide;
        this.preventMove = preventMove;
    }

    private static ArrayList<Collision> collisions = new ArrayList<>();

    public static enum CollisionsType {
        PLAYER,
        SCREEN_BOTTOM,
        ARROW,
        GROUND,
        ENEMY,
        ENEMY_ARROW,
        END
    }

    public static enum CollisionsPosition {
        ALL,
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        NONE
    }

    public static void createNewCollision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide) {
        collisions.add(new Collision(thing, other, position, onCollide));
    }

    public static void createNewCollision(CollisionsType thing, CollisionsType other, CollisionsPosition position, BiConsumer<Thing, Thing> onCollide, boolean preventMove) {
        collisions.add(new Collision(thing, other, position, onCollide, preventMove));
    }

    private static ArrayList<Thing> getThings(Collision c, GameEngine ge) {
        var a = new ArrayList<Thing>();
        for (Thing t : ge.getThings())
            if (t.collisionID == c.thing)
                a.add(t);
        return a;
    }

    private static ArrayList<Thing> getOthers(Collision c, GameEngine ge) {
        var a = new ArrayList<Thing>();
        for (Thing t : ge.getThings())
            if (t.collisionID == c.other)
                a.add(t);
        return a;
    }

    public static void clearCollisions() {
        collisions.clear();
    }
    
    public static void handleCollisions(GameEngine ge) {
        for (Collision c : collisions) {
            for (Thing t : getThings(c, ge)) {
                if (!t.checkCollision)
                    continue;
                FOR:
                for (Thing o : getOthers(c, ge)) {
                    if (!t.collides(o))
                        continue FOR;
                    if (c.onCollide != null)
                        c.onCollide.accept(t, o);
                    if (t.stopMovingOnCollision) {
                        t.dx = 0;
                        t.dy = 0;
                        if (t instanceof Arrow && !((Arrow)t).currentlyFiring)
                            t.checkCollision = false;
                    }
                    if (c.position == CollisionsPosition.NONE)
                        return;
                    if (t.prevY + t.height <= o.y && c.preventMove) {
                        t.y = o.y - t.height;
                        t.dy = 0;
                        t.onground = true;
                    }
                    if (t.prevY >= o.y + o.height && c.preventMove) {
                        t.dy /= 2;
                        t.y = o.y + o.height;
                    }
                    if (!t.collides(o))
                        continue FOR;
                    if (t.prevX + t.width <= o.x && c.preventMove)
                        t.x = o.x - t.width;
                    if (t.prevX >= o.x + o.width && c.preventMove)
                        t.x = o.x + o.width;
                }
            }
        }
    }
}
