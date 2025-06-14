import java.awt.Color;
import java.awt.Graphics;

public class Ground extends Thing {
    public Ground(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.dx = 0;
        this.dy = 0;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
        this.collisionID = Collision.CollisionsType.GROUND;
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
    }

    public static void createGround(GameEngine ge) {
        ge.getThings().add(new Ground(0, ge.getHeight() - 100, 1000, 100));
        ge.getThings().add(new Ground(800, ge.getHeight() - 300, 100, 200));
    }
}
