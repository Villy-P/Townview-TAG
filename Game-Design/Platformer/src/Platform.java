import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform extends Thing {
    public BufferedImage img;
    
    public Platform(double x, double y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.dx = 0;
        this.dy = 0;
        this.width = 80;
        this.height = 10;
        this.collisionID = Collision.CollisionsType.GROUND;
        try {
            this.img = ImageIO.read(new File("assets/image/platformm.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image assets/image/platform.png");
        }
        this.img.getWidth();
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(this.img, null, (int)this.x, (int)this.y);
    }
}
