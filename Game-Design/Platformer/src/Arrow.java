import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends Thing {
    public BufferedImage img;

    public double rotation;

    public boolean update = false;
    public boolean currentlyFiring = true;

    public Thing stickTo = null;

    public double damage = 15;

    public Arrow(double x, double y, double dx, double dy) {
        GameEngine.arrows++;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = 10;
        this.height = 30;
        this.collisionID = Collision.CollisionsType.ARROW;
        this.stopMovingOnCollision = true;
        try {
            this.img = ImageIO.read(new File("assets/image/arrow.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image assets/image/arrow.png");
        }
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(this.rotation, this.x + (this.width / 2), this.y + (this.height / 2));
        g2d.drawImage(this.img, null, (int)this.x, (int)this.y);
        g2d.setTransform(old);
    }

    @Override
    public void update(GameEngine ge) {
        this.prevX = this.x;
        this.prevY = this.y;
        if (this.stickTo != null) {
            this.dx = this.stickTo.x - this.stickTo.prevX;
            this.dy = this.stickTo.y - this.stickTo.prevY;
            this.x += this.dx;
            this.y += this.dy;
            return;
        }
        if ((this.dy != 0 && this.dx != 0) || this.currentlyFiring)
            this.rotation = this.getRotationDegrees(ge);
        this.y += this.dy;
        this.x += this.dx;
        if (!this.update)
            return;
        this.dy += Constants.GRAVITY;
    }

    public double getRotationDegrees(GameEngine ge) {
        final double nextX = this.x + this.dx;
        final double nextY = this.y + this.dy;
        final double rotateBy = Math.atan2(nextY - this.y, nextX - this.x);
        return rotateBy + Math.toRadians(90);
    }
}
