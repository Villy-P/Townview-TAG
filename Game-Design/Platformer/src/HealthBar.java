import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HealthBar extends Thing {
    private Player player;
    public BufferedImage img;

    public HealthBar(Player player) {
        super();
        this.x = 70;
        this.y = 25;
        this.player = player;
        this.cameraAffect = false;
        try {
            this.img = ImageIO.read(new File("assets/image/dash.png"));
        } catch (IOException e) {
            System.out.println("There was an error reading image assets/image/dash.png");
        }
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.drawRect((int)this.x, (int)this.y, (int)player.maxHealth * 2, 20);
        g.setColor(Color.RED);
        g.fillRect((int)this.x + 1, (int)this.y + 1, (int)player.health * 2 - 1, 19);
        g.setColor(Color.BLUE);
        if (this.player.dashing)
            g.fillRect(10, 10 + (int)(this.player.dashSpeed * 2.5), 50, (int)(this.player.maxDashSpeed * 2.5) - (int)(this.player.dashSpeed * 2.5));
        else 
            g.fillRect(10, 10, 50, 50);
        g.drawImage(this.img, 10, 10, null);
    }
}
