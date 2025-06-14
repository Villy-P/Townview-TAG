import java.awt.Color;
import java.awt.Graphics;

public class Thing {
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    protected Color color;

    protected double dx;
    protected double dy;

    public void draw(Graphics g, GameEngine ge) {}
    public void move(GameEngine ge) {}
    public void bounce() {}
    
    public boolean collides(Thing other) {
        return this.x < other.x + other.width &&
               this.y < other.y + other.height &&
               this.x + this.width > other.x &&
               this.y + this.height > other.y;
    }
}
