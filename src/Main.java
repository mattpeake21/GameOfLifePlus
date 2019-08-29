import java.util.Timer;
import java.util.TimerTask;

public class Main{
    //TIMER VARIABLES
    private Timer ticker;
    public Grid grid;
    long lastTick = System.currentTimeMillis();

    public Main()
    {
        grid = Grid.getInstance();
        UserInterface ui = new UserInterface();

        // TICKER
        this.ticker = new Timer();
        this.ticker.schedule(new TimerTask() {
            @Override
            public void run() {

                long time = System.currentTimeMillis();

                lastTick = time;

                if(grid.isRunning()) {
                    grid.nextGeneration();
                }

                ui.repaint(); // Update UI.
            }
        }, 0, 100); // 1000 = 1 Second
    }

    public static void main(String[] args)
    {
        Main application = new Main();
    }
}

