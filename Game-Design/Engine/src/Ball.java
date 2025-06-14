import java.awt.Graphics;
import java.awt.Color;

public class Ball extends Thing {
    private int leftScore;
    private int rightScore;
    private double increaseBy = .0015;

    public Ball() {
        super();
        this.reset();
    }

    public void reset() {
        this.dx = this.dy = 0;
        this.increaseBy = .0015;
        while (dx == 0)
            dx = Random.randInRange(-5, 5);
        while (dy == 0)
            dy = Random.randInRange(-5, 5);
        this.width = this.height = 20;
        this.x = GameEngine.width / 2.0 - this.width / 2.0;
        this.y = GameEngine.height / 2.0 - this.height / 2.0;
        this.color = new Color(
            Random.randInRange(0, 255),
            Random.randInRange(0, 255),
            Random.randInRange(0, 255)
        );
    }

    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillOval((int)this.x, (int)this.y, this.width, this.height);
        g.setColor(Color.white);
        g.drawString("" + this.leftScore, 40, 30);
        g.drawString("" + this.rightScore, GameEngine.width - 45, 30);
        this.move(ge);
    }

    public void bounce() {
        this.dx = -this.dx;
        this.increaseBy += 0.0015;
    }

    public void move(GameEngine ge) {
        this.x += dx;
        this.y += dy;
        this.dx += (this.dx < 0 ? -this.increaseBy : this.increaseBy);
        this.dy += (this.dy < 0 ? -this.increaseBy : this.increaseBy);
        System.out.println(this.dx);
        if (this.x > GameEngine.width) {
            this.leftScore++;
            this.reset();
        }
        if (this.x < -this.width) {
            this.rightScore++;
            this.reset();
        }
        if (this.y > GameEngine.height - this.height || this.y < 0)
            this.dy = -this.dy;
    }
}
