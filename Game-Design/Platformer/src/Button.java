import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
import java.awt.Font;

public class Button extends Thing {
    public String text;

    public Font buttonFont = ResourceLoader.mainFont.deriveFont(20f);

    public BiConsumer<Integer, Integer> onClick;

    public Button(int x, int y, int w, int h, String t) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = t;
        this.cameraAffect = false;
        this.color = Color.BLACK;
    }

    @Override
    public void draw(Graphics g, GameEngine ge) {
        g.setColor(Color.WHITE);
        g.setFont(this.buttonFont);
        g.fillRect((int)this.x - 1, (int)this.y - 1, this.width + 2, this.height + 2);
        g.setColor(this.color);
        g.fillRect((int)this.x, (int)this.y, this.width, this.height);
        
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int stringX = metrics.stringWidth(this.text) / 2;
        g.setColor(Color.WHITE);
        g.drawString(this.text, (int)(this.x + (this.width) / 2) - stringX, (int)this.y + this.height - 10);
        this.update(ge);
    }

    @Override
    public void update(GameEngine ge) {
        if (ge.getInput().isButton(MouseEvent.BUTTON1) && this.mouseInBounds(ge.getInput()))
            this.onClick.accept(ge.getInput().getMouseX(), ge.getInput().getMouseY());
    }

    public boolean mouseInBounds(Input input) {
        int x = input.getMouseX();
        int y = input.getMouseY();
        return x > this.x && 
               y > this.y && 
               x < this.x + this.width && 
               y < this.y + this.height;
    }
}
