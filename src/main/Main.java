package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        // so I can position the location of the frame in the middle of my screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dimension = tk.getScreenSize();

        JFrame window = new JFrame("2D Adventure!");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);

        // displays it at the center of the string
        window.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();

        // starts the game loop
        gamePanel.startGameThread();

        window.add(gamePanel);
        window.pack();
        window.setLocation((int)dimension.getWidth() / 2 - gamePanel.screenWidth / 2, (int)dimension.getHeight() / 2 - gamePanel.screenHeight / 2);

        // Credit to Zach Kohlberg; this makes it so that the gamePanel has focus (which it doesn't usually have)
        // so that it has focus priority over the images in the Player and TileManager classes
        gamePanel.requestFocusInWindow();
    }
}