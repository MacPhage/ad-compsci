// import java.awt.Graphics;
// import java.awt.Color;
// import java.awt.Font;
// import java.awt.event.MouseListener;
// import java.awt.event.MouseEvent;
// import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper extends JPanel implements MouseListener
{
	private int mouseX, mouseY;
	private boolean mouseClicked;
	private Grid mineMap;
	private int rows;
	private int cols;
	private int numOriginalMines;
	private final boolean cellLockEnabled = true;
	private boolean lost;

	public Minesweeper(int numMines, int row, int col)
	{
		mouseClicked = false;
		rows = row;
		cols = col;
		numOriginalMines = numMines;
		this.lost = false;
		mineMap = new Grid(rows,cols);

		//randomly load numMines amount of mines into the grid (make sure you address a mine that would be placed on top of another mine)
		loadGrid(numMines);

		setBackground(Color.white);
		setVisible(true);
		numberOfMines();
		repaint();
		addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e)
	{
		mouseX=e.getX();
		mouseY=e.getY();
		mouseClicked = true;
		repaint();
	}

	public void paintComponent( Graphics window )
	{
		super.paintComponent(window);
		window.setFont(new Font("TAHOMA",Font.BOLD,12));
		window.setColor(Color.blue);
		window.drawString("Project",420,40);
		window.drawString("MINESWEEPER", 420,55);

		//Draw game control buttons (EXTRA CREDIT)

		if (mouseClicked && this.lost == false)
		{
			int c = mouseY/20;
			int r = mouseX/20;
			if(r >= 0 && r < rows && c >= 0 && c < cols) {
				System.out.println("Playing tile ("+r+", "+c+") ...");
				if(mineMap.getSpot(r,c).getMine()) {
					((MineCell)mineMap.getSpot(r,c)).setLose(true);

					if(this.cellLockEnabled) {

						this.lost = true;

						System.out.println("Revealing all cells");
						int temp = 0;
						for(int rr = 0; rr < this.rows; rr++)
						for(int cc = 0; cc < this.cols; cc++) {
							if(mineMap.getSpot(r,c).getMine()) {
								//System.out.println("    MineCell found");
								temp++;
								((MineCell)mineMap.getSpot(r,c)).setLose(true);
							}
						}
						System.out.println("    Attempted to reveal "+temp+" MineCells");
					}

				}
				else {
					play(r,c);
				}
				System.out.println("    Recursion stack complete.");
			}
			else {
				System.out.println("Clicked outside of grid!");

				if(mouseX >= 10 && mouseX <= 10+100 && mouseY >= (20*this.rows)+10 && mouseY <= (20*this.rows)+10+30) {
					System.out.println("    Reset button clicked");
					JOptionPane.showMessageDialog(null, "Minefield will now reset", "Notice", JOptionPane.WARNING_MESSAGE);

					//Make the grid null again

					for(int rr = 0; rr < this.rows; rr++)
					for(int cc = 0; cc < this.cols; cc++) {
						mineMap.setSpot(rr,cc, null);
					}

					loadGrid(this.numOriginalMines);
					numberOfMines();
					repaint();
				}

			}
			mouseClicked = false;
		}
		drawMineGrid(window);
	}

	public void drawMineGrid( Graphics window  )
	{
		//draw the grid
		mineMap.drawGrid(window); //almost rewrote this whole method lol

		//draw reset button
		window.setColor(new Color(0, 170, 170));
		window.fillRect(10, (20*this.rows)+10, 100, 30);

		window.setColor(Color.BLACK);
		window.drawString("Reset minefield", 10, (20*this.rows)+10+16);

		if(this.lost) {

			window.setColor(Color.BLACK);
			window.drawString("Sorry, but you lost. :(", 50+1, 65+1);

			window.setColor(Color.RED);
			window.drawString("Sorry, but you lost. :(", 50, 65);
		}
	}

	public void play( int r, int c )
	{
		//System.out.println("    play("+r+","+c+");");
		//recursively reveal empty cells
		//if a mine is clicked, all mines should be revealed
		if(r >= 0 && r < rows && c >= 0 && c < cols) { //within bounds
			if(!mineMap.getSpot(r,c).getMine() && !((EmptyCell)mineMap.getSpot(r,c)).getVisited()) { //NOT a mine and NOT visited
				((EmptyCell)mineMap.getSpot(r,c)).setVisited(true);
				System.out.println("    play("+r+","+c+");");
				/*play(r-1,c-1);
				play(r-1,c+1);
				play(r+1,c-1);
				play(r+1,c+1);
				play(r,c-1);
				play(r,c+1);
				play(r-1,c);
				play(r+1,c);*/

				for(int rr = -1; rr <= 1; rr++) {
					for(int cc = -1; cc <= 1; cc++) {
						if(((EmptyCell)mineMap.getSpot(r,c)).getCount() == 0) {
							play(r+rr,c+cc);
						}
					}
				}
			}
			/*else if(mineMap.getSpot(r,c).getMine()) {
				for(int r = 0; r < rows; r++)
					for(int c = 0; c < cols; c++) {
						mineMap.getSpot(r,c).draw()
					}
			}*/
		}

	}

	public void numberOfMines()
	{
		//count and set the number of surrounding mines for each cell
		for(int r = 0; r < this.rows; r++)
		for(int c = 0; c < this.cols; c++) {
			if(!mineMap.getSpot(r,c).getMine()) {
				int count = 0;
				for(int rr = -1; rr <= 1; rr++) {
					for(int cc = -1; cc <= 1; cc++) {
						if(r+rr >= 0 && r+rr < rows && c+cc >= 0 && c+cc < cols && mineMap.getSpot(r+rr,c+cc).getMine()) {
							count++;
						}
					}
				}
				((EmptyCell)mineMap.getSpot(r,c)).setCount(count);
			}
		}
	}

	public void loadGrid(int numMines) {
		int temp = 0;
		System.out.println(numMines+" mines requested to be generated. ");

		//randomly load numMines amount of mines into the grid (make sure you address a mine that would be placed on top of another mine)
		for(int i = 0; i < numMines; i++) {
			//System.out.println("Generating MineCell...");
			int temp_r = ((int)Math.floor(Math.random()*(this.rows)));
			int temp_c = ((int)Math.floor(Math.random()*(this.cols)));
			while(mineMap.getSpot(temp_r,temp_c) != null) {
				temp_r = ((int)Math.floor(Math.random()*(this.rows)));
				temp_c = ((int)Math.floor(Math.random()*(this.cols)));
			}
			//Current spot in temp_r,temp_c is confirmed to be null because of the previous loop
			mineMap.setSpot(temp_r,temp_c, new MineCell(temp_r*20, temp_c*20, 20, 20, true));
			//System.out.println("MineCell generated for: ("+temp_r+", "+temp_c+")");
			temp++;
		}
		System.out.println(temp+" mines generated.");

		//then load the rest of the empty cells
		for(int r = 0; r < this.rows; r++)
		for(int c = 0; c < this.cols; c++) {
			if(mineMap.getSpot(r,c) == null) {
				mineMap.setSpot(r,c, new EmptyCell(r*20, c*20, 20, 20, false));
			}
		}
	}



	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
}
