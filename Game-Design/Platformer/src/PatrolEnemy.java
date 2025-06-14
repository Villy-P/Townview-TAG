import java.awt.Color;

public class PatrolEnemy extends Enemy {
    public int patrolFor = 100;
    public int currentPatrolVal = patrolFor;
    public int patrolSpeed = 5;

    public PatrolEnemy(double x, double y) {
        super();
        this.x = x;
        this.y = y;
        this.dx = this.patrolSpeed;
        this.height = this.width = 50;
        this.color = Color.PINK;
        this.healthBarWidth = this.width - 20;
    }

    @Override
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        if (this.dead)
            return;
        this.dy += Constants.GRAVITY;
        this.x += this.dx;
        this.y += this.dy;

        this.currentPatrolVal--;
        if (this.currentPatrolVal == 0) {
            this.currentPatrolVal = this.patrolFor;
            this.dx = -this.patrolSpeed;
            this.patrolSpeed = -this.patrolSpeed;
        }
        if (this.health <= 0) {
            this.dead = true;
            this.dx = 0;
            this.dy = 0;
        }
    }
}
