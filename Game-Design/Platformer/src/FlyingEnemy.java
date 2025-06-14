import java.awt.Color;

public class FlyingEnemy extends Enemy {
    public Player p;

    public FlyingEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.height = this.width = 50;
        this.color = Color.BLUE;
        this.healthBarWidth = this.width - 20;
        this.maxHealth = 50.0;
        this.health = 50.0;
        this.onground = false;
        this.stopMovingOnCollision = true;
    }

    @Override
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        this.y += this.dy;
        if (this.dead) {
            this.health = 0;
            this.dx = 0;
            this.dy += Constants.GRAVITY;
            return;
        }
        this.x += this.dx;
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
        this.setVelocity(ge);
    }

    public void setVelocity(GameEngine ge) {
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        double forceX = Math.toDegrees(Math.sin(angle)) * 0.05;
        double forceY = Math.toDegrees(Math.cos(angle)) * 0.05;
        this.dx = forceX;
        this.dy = -forceY;
    }
}
