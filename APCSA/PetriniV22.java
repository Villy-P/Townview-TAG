public class PetriniV22 {
    public static int randomIntFromInterval(int min, int max) { // min and max included 
        return (int)(Math.random() * (max - min + 1) + min);
    }

	public static void main(String args[]) {
		PetriDish p = new PetriDish();
		p.showGrid();
		/*Change the (4,4) to be a random valid value within the grid*/
		System.out.println(p.numNeighbors(randomIntFromInterval(0, p.getGrid().length - 1), randomIntFromInterval(0, p.getGrid()[0].length - 1)));
	}
}


class PetriDish {
	private boolean[][] grid;

	/*Write the PetriDish constructor.
	 *A PetriDish is a matrix of 5-30 rows and 5-30 columns (randomly generated).
	 *Each position in a PetriDish has a 30% of being populated (true).
	*/

    public boolean[][] getGrid() {
        return this.grid;
    }

    public PetriDish() {
        this.grid = new boolean[PetriniV22.randomIntFromInterval(5, 30)][PetriniV22.randomIntFromInterval(5, 30)];
        for (int r = 0; r < this.grid.length; r++)
            for (int c = 0; c < this.grid[r].length; c++)
                this.grid[r][c] = PetriniV22.randomIntFromInterval(1, 100) < 30;
    }


	/*Complete the showGrid method which displays the current state of the grid.*/
    public void showGrid() {
        for (boolean[] r : this.grid) {
            for (boolean c : r)
                System.out.print(c ? "O" : ".");
            System.out.println();
        }
    }

    /*Complete the method numNeighbors which returns the number of adjacent true elements in the grid
    *Postcondition: the return value is a value from 0 to 8, inclusive*/
    public int numNeighbors(int row, int col) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if ((r == row && c == col) || r < 0 || r > grid.length - 1 || c < 0 || c > grid[0].length - 1)
                    continue;
                if (this.grid[r][c])
                    count++;
            }
        }
        return count;
    }
}