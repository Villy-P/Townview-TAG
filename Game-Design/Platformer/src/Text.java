import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text extends Thing {
    public String text;

    public Font font = ResourceLoader.mainFont.deriveFont(30f);

    public Text(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.height = 5000;
        this.width = 5000;
        this.dx = 0;
        this.dy = 0;
        this.text = text;
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.setFont(this.font);
        g.drawString(this.text, (int)this.x, (int)this.y);
    }
}
