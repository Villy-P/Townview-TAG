import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Thing {
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    protected Color color;

    protected double dx;
    protected double dy;

    protected Collision.CollisionsType collisionID;

    public boolean cameraAffect = true;
    public boolean onground = false;
    public boolean stopMovingOnCollision = false;
    public boolean checkCollision = true;

    public double prevX;
    public double prevY;

    public void draw(Graphics g, GameEngine ge) {}
    public void update(GameEngine ge) {}
    public void bounce() {}

    public double getCenterX() {
        return this.width / 2;
    }

    public double getCenterY() {
        return this.height / 2;
    }
    
    public boolean collides(Thing other) {
        return this.x < other.x + other.width &&
               this.y < other.y + other.height &&
               this.x + this.width > other.x &&
               this.y + this.height > other.y;
    }

    public Thing getCollisionOverlap(Thing other) {
        double[] horizontal = {this.x, this.x + this.width, other.x, other.x + other.width};
        double[] vertical = {this.y + this.height, this.y, other.y + other.height, other.y};
        Arrays.sort(horizontal);
        Arrays.sort(vertical);
        Thing retValue = new Thing();
        retValue.x = horizontal[1];
        retValue.y = vertical[1];
        retValue.width = (int)horizontal[2] - (int)retValue.x;
        retValue.height = (int)vertical[2] - (int)retValue.y;
        return retValue;
    }

    public static boolean isInFrame(Thing t, GameEngine ge) {
        final Camera c = ge.getCamera();
        return t.x - 100 < c.x + ge.getWidth() && t.x + t.width + 100 > c.y && t.y - 100 < c.y + ge.getHeight() && t.y + t.height + 100 > c.y;
    }
}