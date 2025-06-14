import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Thing {
    public double maxHealth = 100.0;
    public double health = 100.0;

    public double healthBarWidth;

    public boolean dead = false;

    public Enemy() {
        this.collisionID = Collision.CollisionsType.ENEMY;
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
        g.setColor(Color.RED);
        g.fillRect((int)this.x + 10, (int)this.y - 10, (int)((this.healthBarWidth / this.maxHealth) * this.health), 5);
    }
}
