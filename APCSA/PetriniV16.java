import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class PetriniV16 extends Canvas {
	/*Declare your two dimensional array of TestTaker objects called "grid" here*/
    TestTaker[][] grid = new TestTaker[6][5];

	/**Sets up the GUI window
	 *Do not alter this code*/

	public JFrame frame = new JFrame("Lab 16");
	public static Graphics g;

	public void windowSetup() {
		Dimension size = new Dimension(1200,800);
		frame.setPreferredSize(size);
		frame.setMinimumSize(size);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.setVisible(true);
	}

	public static int randomNumber(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

	public void draw() {
		BufferStrategy buffer = this.getBufferStrategy();
		if(buffer==null) { 
            this.createBufferStrategy(3);
            return; 
        }
		g = buffer.getDrawGraphics();
		drawDesks();
		g.dispose();
		buffer.show();
	}
    
	public void render(Graphics g) {

	}

	public PetriniV16() {
		fillDesks();
		windowSetup();
		while (true)
			draw();
	}

	public static void main(String[] args) {
		new PetriniV16();
	}

	/*Display the seating chart */
	public void drawDesks() {
		/*Changes the Font to 30 point Arial*/
		g.setFont(new Font("Courier New",0,30));
		/*Displays a single rectangle on the screen with the following parameters
		 *(x start, y start, width, height)*/
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[0].length; c++) {
		 		g.setColor(Color.black);
		 		g.drawRect(c*200+50,r*100+50,150,75);
				/*Displays a String on the screen with the following parameters
				 *(String to display, x value, y value)*/
                if(grid[r][c] != null) {
                    g.drawString("" + grid[r][c].getNumber() + " :" + grid[r][c].getID(), c * 200 + 58, r * 100 + 85);
                    g.drawString("" + grid[r][c].getAvg(), c * 200 + 110, r * 100 + 120);
                }
		 	}
        }
	}

	/*Initialize the two dimension array of TestTaker objects to have 6 rows and 5 columns
	 *Populate the seating chart with TestTaker objects
	 *Each TestTaker is assigned a number, beginning at 1
	 *Post-condition : Every index of the 2D array will have a TestTaker object with a unique number*/
	public void fillDesks() {
		for (int i = 1; i <= 30; i++) {
			int row = randomNumber(0, 5);
			int col = randomNumber(0, 4);
			if (grid[row][col] == null)
				grid[row][col] = new TestTaker(i);
			else 
				i--;
		}
	}
}

/* You will have to change the Student class name below to your Student class name (Ex: SmithStudent)*/
class TestTaker extends Student {
	private int number;

	public TestTaker(int num) {
		super();
		number = num;
	}

	public int getNumber() {
		return number;
	}
}

/*You will need to paste your Student class here
 *and name it appropriately (Student).
 *The class may need to be altered to be able to use
 *the getAvg() and getID() methods.*/
class Student {
	private int id = PetriniV16.randomNumber(1000, 9999);
    private int[] tests = new int[3];

    public Student() {
        for (int i = 0; i < 3; i++)
            tests[i] = PetriniV16.randomNumber(70, 100);
    }

	public int getID() {
		return id;
	}

    public int getAvg() {
        int total = 0;
        for (int testScore : this.tests)
            total += testScore;
        return total / 3;
    }
}