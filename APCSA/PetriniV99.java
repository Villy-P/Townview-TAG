/* This lab encapsulates on of the scenes in the book Persepolis that we read in Honors English class.
 * This lab showcases the scene where the war between two nations happened, and the bombs that dropped.
 * Two bombs consistantly drop on the buildings, and a car can be seen driving around. Buildings line
 * the skyline and the sun goes across the sky. A constant stream of text on the top of the screen shows
 * how authorities responded to this attack by warning them
 */

import java.awt.*;
import java.util.*;

public class PetriniV99 extends Panel {
	private Image offScreenImage;
    private Dimension offScreenSize;
    private Graphics offScreenGraphics;
    final boolean RESIZEABLE = false;
	public static int width = 1280 - 10;
    public static int height = 1024 - 10;
    Image virtualMem;
    Graphics2D gBuffer;

	private ArrayList<Object99> objects = new ArrayList<>();

	public void resizeWindow() {
        if(RESIZEABLE) {
            if(getHeight() != height + 10 || getWidth() != width + 10) {
                height = getHeight() - 10;
                width = getWidth() - 10;
                virtualMem = createImage(width + 20, height + 20);
		        gBuffer = (Graphics2D)virtualMem.getGraphics();
                gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        } else {
            setSize(width+10,height+10);
        }
    }

	public void update(Graphics g) {
        Dimension d = size();
        if((offScreenImage == null) || (d.width != offScreenSize.width) || (d.height != offScreenSize.height)) {
            offScreenImage = createImage(d.width, d.height);
		    offScreenSize = d; 
            offScreenGraphics = offScreenImage.getGraphics();
        }
        offScreenGraphics.clearRect(0, 0, d.width, d.height);
        paint(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, null);
    }

	public static void main(String[] args) {
  		Frame f = new Frame();
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        PetriniV99 window = new PetriniV99();  //change gamestart to your file name   (twice in this line)
  		window.setSize(1050, 700);
        f.add(window);
        f.pack();
        window.init();
  		f.setSize(1050,700);
		f.show();
	}
	
	public void init()  {
		objects.add(new Sky(0, 0));
		objects.add(new Bomb(900, -10));
		objects.add(new Bomb(800, -20));
		objects.add(new Ground(0, 501));
		objects.add(new Building(100, 200, 150, 300));
		objects.add(new Building(400, 425, 50, 75));
		objects.add(new Building(900, 325, 150, 175));
		objects.add(new Sun(30, 30));
		objects.add(new Warning(0, 50));
		objects.add(new Car(0, 455));
	}
	
	public void paint(Graphics g) {
		for (Object99 i : objects) {
			i.draw(g);
			i.move(g);
		}


		try { 
			Thread.sleep(20); 
		} catch (Exception e) {}
		repaint();
	}
	//write additional methods here

}

class Object99 {
	protected int x, y;

	public Object99(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {}

	public void move(Graphics g) {}
}

class Ground extends Object99 {
	public int count = 5;

	public Ground(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(0, 500, 1200, 500);
		g.setColor(Color.green);
		g.fillRect(0, 501, 1200, 1200);
	}
}

class Sky extends Object99 {
	public Sky(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 1200, 900);
	}
}

class Bomb extends Object99 {
	int orgX;

	public Bomb(int x, int y) {
		super(x, y);
		this.orgX = x;
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x-10, y+90, 20, 20);

		g.setColor(Color.darkGray);
		int[] xs = {x, x + 10, x + 100, x + 90};
		int[] ys = {y + 90, y +100, y + 10, y};
		g.fillPolygon(xs, ys, 4);
	}

	public void move(Graphics g) {
		x -= 6;
		y+=6;
		if (y > 1200) {
			y = -100;
			x = orgX;
		}
	}
}

class Building extends Object99 {
	private int width, height;

	public Building(int x, int y, int width, int height) {
		super(x,y);
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(230, 113, 18));
		g.fillRect(x, y, width, height);

		g.setColor(Color.darkGray);
		g.fillRect(x + 10, y + 10, width - 20, height / 3);
	}
}

class Sun extends Object99 {
	public Sun(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(x, y, 100, 100);
	}

	public void move(Graphics g) {
		x += 1;
	}
}

class Warning extends Object99 {
	public Warning(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		g.drawChars("WARNING: MISSILE STRIKE INBOUND. PLEASE EVACUATE.\t".repeat(100).toCharArray(), 0, 4900, x, y);
	}

	public void move(Graphics g) {
		x -= 1;
	}
}

class Car extends Object99 {
	public Car(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x, y + 30, 15, 15);
		g.fillOval(x + 40, y + 30, 15, 15);
		g.fillRect(x - 5, y + 16, 70, 21);
		g.fillRect(x, y + 4, 55, 13);
	}

	public void move(Graphics g) {
		x+=2;
	}
}