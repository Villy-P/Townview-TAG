import java.awt.*;
import javax.swing.JFrame;//JFrame
import java.awt.image.BufferStrategy;

public class PetriniV26 {
	public static void main(String[] args) {
		ParkingLotLab.main(args);
	}
}

class ParkingLot {
	private Car[][] spaces;

	public ParkingLot() {
		spaces = new Car[5][13];
        for (int r = 0; r < 5; r++)
            for (int c = 0; c < 13; c++)
                if (Math.random() < .7)
                    spaces[r][c] = new Car();
	}

	public boolean laneFull(int row) {
        for (int c = 0; c < 13; c++)
            if (spaces[row][c] == null)
                return false;
		return true;
	}

	public boolean lotFull() {
        for (int r = 0; r < 5; r++)
            if (!laneFull(r))
                return false;
		return true;
	}

	public void parkCar(Car car) {
        for (int r = 0; r < 5; r++) {
            for (int c = 12; c >= 0; c--) {
                if (this.spaces[r][c] == null) {
                    this.spaces[r][c] = new Car();
                    return;
                }
            }
        }
	}

	public void draw(Graphics g) {
		ParkingLotLab.drawLot(g, this);
		for(int r = 0; r < spaces.length; r++) {
			for(int c = 0;c < spaces[0].length; c++) {
				ParkingLotLab.drawSpace(g, r, c);
				if(spaces[r][c]!=null)
					spaces[r][c].draw(g, r, c);
			}
		}
		ParkingLotLab.drawFull(g, lotFull());

	}

	public Car[][] getSpaces(){
        return spaces;
    }
}


















































































































































































































































































class ParkingLotLab extends Canvas implements Runnable
{
	private static final int WIDTH = 1020, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	private ParkingLot p = new ParkingLot();
	public static int getLotWidth()	{		return WIDTH;	}
	public static int getLotHeight()	{		return HEIGHT;	}

	public ParkingLotLab()//Constructor
	{
		new Window(WIDTH, HEIGHT, "Game", this);
	}

	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running =true;
	}
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames =0;
		while(running)
		{
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			while(delta>=1)
			{
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer+=1000;
				System.out.println("FPS: "+frames);
				frames = 0;
			}


			}
			stop();

	}
	int counter=500;
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,WIDTH,HEIGHT);
		if(counter<0)
		{
			p.parkCar(new Car());
			counter=500;
		}
		counter--;
		p.draw(g);
		g.dispose();
		bs.show();
	}
	public void tick()
	{

	}
		public static void drawLot(Graphics g, ParkingLot p)
	{
		g.setColor(new Color(0x0e6e18));
		g.fillRect(0,0,ParkingLotLab.getLotWidth(),ParkingLotLab.getLotHeight());
		g.setColor(new Color(0x737373));
		g.fillRect(50,50,ParkingLotLab.getLotWidth()-110,ParkingLotLab.getLotHeight()-130);
		g.fillRect(50,ParkingLotLab.getLotHeight()/2,ParkingLotLab.getLotWidth(),128);
		for(int r=0;r<p.getSpaces().length;r++)
		{
			if(!p.laneFull(r))
				g.setColor(Color.green);
			else
				g.setColor(Color.red);
			g.fillOval(900,r*128+50+27+r%2*64,10,10);
		}
	}
	public static void drawFull(Graphics g, boolean full)
	{
			if(full)
		{
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0,0,ParkingLotLab.getLotWidth(),ParkingLotLab.getLotHeight());
			g.setColor(Color.red);
			g.setFont(new Font("Arial",0,120));
			g.drawString("Lot Full",ParkingLotLab.getLotWidth()/2-150,ParkingLotLab.getLotHeight()/2-150);
		}
	}
	public static void drawSpace(Graphics g,int r, int c)
	{
		g.setColor(Color.white);
		g.fillRect(c*64+50,r*128+50+r%2*64,4,64);
		g.fillRect(c*64+50+64,r*128+50+r%2*64,4,64);
		if(r%2==0)
		{
			g.fillRect(c*64+50,r*128+50+r%2*64,64,2);
		}
	}
	public static void main(String args[])
	{
		new ParkingLotLab();
	}
}
class Window extends Canvas
{
	public Window(int w, int h, String t, ParkingLotLab game)
	{
		JFrame frame = new JFrame(t);
		frame.setPreferredSize(new Dimension(w,h));
		frame.setMinimumSize(new Dimension(w,h));
		frame.setMaximumSize(new Dimension(w,h));

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
		game.start();

	}
}
class Car
{
	private Color color;
	private static int number=1;
	private int num;
	public Car()
	{
		color = new Color((int)(Math.random()*0xffffff));
		num=number;
		number++;
	}
	public void draw(Graphics g,int row, int col)
	{
		g.setColor(color);
		g.fillRect(col*64+50+8,row*128+54+row%2*64,50,56);
		g.setColor(Color.black);
		g.setFont(new Font("Arial",0,12));
		g.drawString(""+num,col*64+84,row*128+74+row%2*64);
	}
}