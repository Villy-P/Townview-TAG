import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Instructions extends Thing {
    public Button b = new Button(30, 320, 200, 30, "Exit");

    public Font font = ResourceLoader.mainFont.deriveFont(30f);
    public int yOff = 30;

    public Instructions(GameEngine ge) {
        this.b.onClick = (t, u) -> {
            ge.getThings().remove(this);
            ge.showInstructions = false;
        };
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ge.getWidth(), ge.getWidth());

        g.setColor(Color.WHITE);
        g.setFont(this.font);

        g.drawString("WAD to move", 30, 30 + this.yOff);
        g.drawString("Space to Jump", 30, 70 + this.yOff);
        g.drawString("Shift to dash", 30, 110 + this.yOff);
        g.drawString("S to crouch", 30, 150 + this.yOff);
        g.drawString("Press & hold left mouse button to begin charging arrow shot", 30, 190 + this.yOff);
        g.drawString("The longer you hold, the farther it goes", 30, 230 + this.yOff);
        g.drawString("Release the mouse button to fire", 30, 270 + this.yOff);

        b.draw(g, ge);
    }
}
