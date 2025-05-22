package main;
import Entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

// subclass of the JPanel, so it has every method in JPanel
// Runnable to use the Thread
public class GamePanel extends JPanel implements Runnable {
    
    // 16x16 tile size; we must scale this
    private static final int DEFAULT_TILE_SIZE = 16;

    // this makes it so that the tiles we make appear to be 48x48 on the screen, although the tiles themselves are 16x16
    final int scale = 3;

    // 48x48: actual tile size
    public final int tileSize = DEFAULT_TILE_SIZE * scale; 

    // ratio is 4:3
    public final int maxScreenColumns = 16;
    public final int maxScreenRows = 12;

    // to display a 16x12 screen: 768x576 game window
    public final int screenWidth = maxScreenColumns * tileSize;
    public final int screenHeight = maxScreenRows * tileSize;

    // thread is something we can start and stop
    // once it starts, it continues going before stopped
    Thread gameThread;

    // for keyboard input
    KeyboardInputHandler keyboard = new KeyboardInputHandler();

    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyboard);

    // FPS
    int FPS = 60;

    // we pass this instance of gamePanel and the keyboard object declared above in this class
    // this lets us use this gamePanel and keyboard from the player class

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        // if this is true, all the drawings from this component will be done in an offscreen painting buffer
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboard);
        this.setFocusable(true);
    }

    public void startGameThread() {
        // we are passing the GamePanel to this thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    // when we start the gameThread, this run method will be automatically called
    // we create the game loop here, which is the core of our game
    @Override
    public void run() {
        // as long as this gameThread exists, it will keep executing the code here
        while (gameThread != null) {

            // this equates to around 0.01666 (repeating) seconds, which means that it will redraw this 60 times per second
            double drawInterval = 1000000000/FPS; 
            double nextDrawTime = System.nanoTime() + drawInterval;

                // In this method, we should:
                // 1. Update information such as the characters' positions.
                update();

                // 2. Re-draw the screen with said updated information.
                // repaint() is how we call the paintComponent method.
                repaint();

                // The FPS of the GamePanel will do these things (FPS value) per second.

            

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                // converting from nanoseconds to milliseconds
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // moves by 4 pixels; 4 being the value of the playerSpeed variable
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // here, we convert the Graphics g to a Graphics2D class
        // Graphics2D has more functions to exercise more control over more sophisticated graphics 
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);

        // this removes the Graphics2D after the drawing is complete; this saves some memory
        g2.dispose();

        // this.setBackground(new Color(
        //     (int) (Math.random() * 256),
        //     (int) (Math.random() * 256),
        //     (int) (Math.random() * 256)));
        // try {
        //     Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

}
