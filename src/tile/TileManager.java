package tile;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    
    // Array of tiles; could possibly employ an ArrayList
    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        
        getTileImage();
    }

    /**    
     * Reads the tile pngs.
     * Pre-conditions: all tile pngs are valid and accessible
     * Post-conditions: all tile pngs are stored in the tile array
    */
    public void getTileImage() {
        File grassImage = new File("./src/res/tiles/grass.png");
        File waterImage = new File("./src/res/tiles/water.png");
        File wallImage = new File("./src/res/tiles/wall.png");
        
        try {
            // 0 means a grass tile.
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(grassImage);

            // 1 means a water tile.
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(waterImage);

            // 2 means a wall tile.
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(wallImage);
        }

        catch(IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Draws tiles in a specified manner.
     * Preconditions: gamePanel is initialized with its default values
     * Postconditions: the tiles are drawn in the requested way
    */
    public void draw(Graphics2D g2) {

        int columns = 0;
        int rows = 0;
        int x = 0;
        int y = 0;

        while (columns < gamePanel.maxScreenColumns && rows < gamePanel.maxScreenRows) {
            g2.drawImage(tile[0].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            columns++;
            x += gamePanel.tileSize;

            if (columns == gamePanel.maxScreenColumns) {
                columns = 0;
                x = 0;
                rows++;
                y += gamePanel.tileSize;
            }
        }

    }
}