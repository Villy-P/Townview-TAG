import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetriniV19 {
	public static void main(String[] args) {
		ColorGrid.main(args);
	}
}

class Box {
	private static Color[] colors = {Color.red, Color.blue, Color.yellow, Color.magenta, Color.cyan, Color.white};
	Color color;
	int x;
    int y;

	public Box(int row, int col) {
		newColor();
		x = col;
		y = row;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x * 50 + 1, y * 50 + 1, 48, 48);
	}

	public void newColor() {
		color = colors[(int)(Math.random() * colors.length)];
	}

	public String toString() {
		return x + "," + y + "\t" + color;
	}

	public static Color[] getColors() {
		return Arrays.copyOf(colors, colors.length);
	}
}

class ColorGrid extends Canvas implements Runnable {
	public static void main(String args[]) {
        new ColorGrid();
    }

	public void fillColor(Box b) {
		changeColors(b.color, b.x, b.y, new ArrayList<>());

		palette.newColor();
	}

	public void changeColors(Color color, int x, int y, List<Box> boxes) {
		if (x < 0 || y < 0 || x >= 8 || y >= 8)
			return;
		Box b = this.grid[y][x];
		if (!b.color.equals(color) || boxes.contains(b))
			return;
			boxes.add(b);
		b.color = palette.color;

		changeColors(color, x + 1, y, boxes);
		changeColors(color, x - 1, y, boxes);
		changeColors(color, x, y + 1, boxes);
		changeColors(color, x, y - 1, boxes);
	}


	private static final int WIDTH = 640;
    private static final int HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	private boolean running = false;
	private Box[][] grid = new Box[8][8];

	Box palette = new Box(1, 11);
	Input mouse = new Input();

	public ColorGrid() {
		addMouseListener(mouse);
		requestFocus();
		for (int row = 0; row < grid.length; row++)
			for (int col = 0; col < grid[0].length; col++)
				grid[row][col] = new Box(row,col);
		new Window(WIDTH, HEIGHT, "Color Fill", this);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try	{
            thread.join();
            running = false;
        } catch(Exception e){}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1){
                tick();
                delta--;
            }
			if (running)
                render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {	
                timer+=1000;
                System.out.println("FPS: "+frames);
                frames = 0;
            }
		}
			stop();

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if ( bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		palette.draw(g);
		for (Box[] row : grid) {
			for (Box b: row) {
				b.draw(g);
				checkClick(b);
			}
        }
		g.dispose();
		bs.show();
	}

	public void checkClick(Box b) {
		if(	mouse.x < b.x * 50 + 48 &&
			mouse.y < b.y * 50 + 48 &&
			mouse.x > b.x * 50 &&
			mouse.y > b.y * 50) {
				fillColor(b);
				mouse.x = 1000;
                mouse.y = 1000;
			}
	}

	public void tick() {	}
}

class Window extends Canvas {
	public Window(int w, int h, String t, ColorGrid game) {
		JFrame frame = new JFrame(t);
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));
		frame.setMaximumSize(new Dimension(w, h));

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();

	}
}

class Input extends MouseAdapter {
	int x;
    int y;

	public void mouseClicked(MouseEvent e) {
		if (e.getX() != x || e.getY() != y) {
            x = e.getX();
            y = e.getY();
		}
	}
}