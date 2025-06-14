import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GameOver extends Thing {
    public boolean draw = true;

    public Font gameOverFont = ResourceLoader.mainFont.deriveFont(70f);

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ge.getWidth(), ge.getWidth());

        g.setFont(this.gameOverFont);
        
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int stringX = metrics.stringWidth("GAME OVER") / 2;
        g.setColor(Color.RED);
        g.drawString("GAME OVER", (ge.getWidth() / 2) - stringX, 300);

        Button b = new Button(ge.getWidth() / 2 - 100, 500, 200, 30, "Restart");
        Button b2 = new Button(ge.getWidth() / 2 - 100, 550, 200, 30, "Quit");

        b.onClick = (x, y) -> {
            ge.setup();
        };
        b2.onClick = (x, y) -> {
            System.exit(0);
        };

        b.draw(g, ge);
        b2.draw(g, ge);
    }
}
