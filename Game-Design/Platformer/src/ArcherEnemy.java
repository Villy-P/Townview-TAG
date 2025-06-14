import java.awt.Color;

public class ArcherEnemy extends Enemy {
    public Player p;

    public Arrow currentArrow = null;

    public boolean firing = false;

    public double maxShootingTime = 20.0;
    public double shootingTime = 20.0;
    public double shootingReduceTime = 0.2;

    public ArcherEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.height = this.width = 50;
        this.color = Color.ORANGE;
        this.healthBarWidth = this.width - 20;
        this.maxHealth = 80.0;
        this.health = 80.0;
        this.onground = false;
        this.stopMovingOnCollision = true;
    }

    @Override
    public void update(GameEngine ge) {
        this.shootingTime -= this.shootingReduceTime;
        if (this.shootingTime <= 0) {
            this.releaseArrow(ge);
            this.shootingTime = this.maxShootingTime;
        }
        this.prevX = this.x;
        this.prevY = this.y;
        this.dy += Constants.GRAVITY;
        this.y += this.dy;
        if (this.dead) {
            this.health = 0;
            this.dy = 0;
            return;
        }
        this.x += this.dx;
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
        if (!this.firing)
            this.beginShootArrow(ge);
        if (this.currentArrow != null)
            this.setRotationalForce(ge);
    }

    public void beginShootArrow(GameEngine ge) {
        this.currentArrow = new Arrow(this.x, this.y, 0, 0);
        this.currentArrow.checkCollision = false;
        ge.getThings().add(currentArrow);
        this.firing = true;
    }

    public void setRotationalForce(GameEngine ge) {
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        this.currentArrow.rotation = angle;
    }

    public void releaseArrow(GameEngine ge) {
        if (this.currentArrow == null)
            return;
        double dx = this.p.x - this.x;
        double dy = this.p.y - this.y;
        double angle = Math.atan2(dy, dx) + Math.toRadians(90);
        double forceX = Math.toDegrees(Math.sin(angle)) * Constants.MAX_ARROW_FORCE;
        double forceY = Math.toDegrees(Math.cos(angle)) * Constants.MAX_ARROW_FORCE;
        this.currentArrow.dx = forceX;
        this.currentArrow.dy = -forceY;
        this.currentArrow.update = true;
        this.currentArrow.cameraAffect = true;
        this.currentArrow.currentlyFiring = false;
        this.currentArrow.collisionID = Collision.CollisionsType.ENEMY_ARROW;
        this.currentArrow.checkCollision = true;
        this.currentArrow = null;
        this.firing = false;
    }
}
