package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dimension = tk.getScreenSize();
        // System.out.println(String.format("Width: %d\nHeight:%d", (int)dimension.getWidth(), (int)dimension.getHeight()));

        JFrame window = new JFrame("2D Adventure!");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);

        // displays it at the center of the string
        window.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();

        gamePanel.startGameThread();

        window.add(gamePanel);
        window.pack();
        window.setLocation((int)dimension.getWidth() / 2 - gamePanel.screenWidth / 2, (int)dimension.getHeight() / 2 - gamePanel.screenHeight / 2);

        gamePanel.requestFocusInWindow();
    }
}