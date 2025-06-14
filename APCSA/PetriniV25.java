public class PetriniV25 {
    public static int randomIntFromInterval(int min, int max) { // min and max included 
        return (int)(Math.random() * (max - min + 1) + min);
    }

	public static void main(String args[]) {
		PetriDish p = new PetriDish();
		p.showGrid();
		System.out.println(
            p.numNeighbors(
                randomIntFromInterval(0, p.getGrid().length - 1), 
                randomIntFromInterval(0, p.getGrid()[0].length - 1)));
        p.updateGrid();
        p.showGrid();
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
        this.grid = new boolean[PetriniV25.randomIntFromInterval(5, 30)][PetriniV25.randomIntFromInterval(5, 30)];
        for (int r = 0; r < this.grid.length; r++)
            for (int c = 0; c < this.grid[r].length; c++)
                this.grid[r][c] = PetriniV25.randomIntFromInterval(1, 100) < 30;
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

    public boolean updateCell(int row, int col) {
        if (this.grid[row][col]) {
            if (this.numNeighbors(row, col) < 2 || this.numNeighbors(row, col) > 3)
                return false;
            return true;
        } else {
            if (row == 0 || row == grid.length - 1 || col == 0 || col == grid[0].length - 1)
                return true;
            return false;
        }
    }

    public void updateGrid() {
        boolean[][] newBool = new boolean[this.grid.length][this.grid[0].length];
        for (int r = 0; r < this.grid.length; r++)
            for (int c = 0; c < this.grid[r].length; c++)
                newBool[r][c] = updateCell(r, c);
        this.grid = newBool;
    }
}