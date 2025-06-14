import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;

public class Paddle extends Thing {
    private int playerNumber;
    private double moveBy = 5;

    public Paddle(int playerNumber) {
        this.playerNumber = playerNumber;
        this.width = 15;
        this.height = 100;
        this.color = (playerNumber == 1 ? Color.CYAN : Color.MAGENTA);
        this.y = GameEngine.height / 2 - this.height / 2;
        this.x = 25;
        if (this.playerNumber == 2)
            this.x = GameEngine.width - this.width - 25;
    }

    public void draw(Graphics g, GameEngine ge) {
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
        this.move(ge);
        this.moveBy += 0.0015;
    }

    public void move(GameEngine ge) {
        if (this.playerNumber == 1) {
            if (ge.getInput().isKeyDown(KeyEvent.VK_W))
                y += -this.moveBy;
            if (ge.getInput().isKeyDown(KeyEvent.VK_S))
                y += this.moveBy;
        } else {
            if (ge.getInput().isKeyDown(KeyEvent.VK_UP))
                y += -this.moveBy;
            if (ge.getInput().isKeyDown(KeyEvent.VK_DOWN))
                y += this.moveBy;
        }
        if (this.y < 0)
            this.y = 0;
        if (this.y > GameEngine.height - this.height)
            this.y = GameEngine.height - this.height;
    }
}
