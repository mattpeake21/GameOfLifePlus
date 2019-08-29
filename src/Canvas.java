import javax.swing.*;
import java.awt.*;

// Drawing area class (inner class).
class Canvas extends JPanel
{
    Grid grid = Grid.getInstance();

    // Called every time there is a change in the canvas contents.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    // Called by the canvas' paintComponent method
    void draw(Graphics g)
    {
        int aliveCount = 0, deadCount = 0;

        boolean[][] gridArray = grid.getGrid();
        for(int row = 0; row < gridArray.length; row++){
            for(int col = 0; col < gridArray[row].length; col++){
                if(gridArray[row][col]){
                    if(grid.isInverted()) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(Color.black);
                    }
                    aliveCount++;
                } else {
                    if(grid.isInverted()) {
                        g.setColor(Color.black);
                    } else {
                        g.setColor(Color.white);
                    }
                    deadCount++;
                }
                g.fillRect(col * (grid.getCellSize() + grid.getCellInnerGap()) + grid.getCellOuterGap() , row * (grid.getCellSize() + grid.getCellInnerGap()) + grid.getCellOuterGap(), grid.getCellSize(), grid.getCellSize());
            }
        }

        UserInterface.infoLabel.setText("<html><center>Generation: " + grid.getGeneration() + "</center>Alive: " + aliveCount + " ; Dead: " + deadCount + "</html>");
    } // end draw method
} // end inner class Canvas
