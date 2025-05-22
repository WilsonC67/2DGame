package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
    GamePanel gamePanel;
    
    // Array of tiles; could possibly employ an ArrayList
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];

        // this will store all the numbers from a map txt file
        mapTileNumber = new int[gamePanel.maxScreenColumns][gamePanel.maxScreenRows];
        
        getTileImage();
        readMapFromFile("./src/res/maps/map1.txt");
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

            // 1 means a wall tile.
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(wallImage);

            // 2 means a water tile.
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(waterImage);
        }

        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void readMapFromFile(String filePath) {
        try {

            // for some reason, using .getClass().getResourceAsStream("filepath") doesn't work at all in this project
            // so I created a File, made a fileInputStream rather than a InputStream and passed it to the BufferedReader
            // imports the map file, and then the BufferedReader will read from it
            File mapFile = new File(filePath);
            FileInputStream fileStream = new FileInputStream(mapFile);
            BufferedReader mapReader = new BufferedReader(new InputStreamReader(fileStream));

            int column = 0;
            int row = 0;

            while (column < gamePanel.maxScreenColumns && row < gamePanel.maxScreenRows) {
                // reads a line at a time
                String line = mapReader.readLine();

                while (column < gamePanel.maxScreenColumns) {
                    // this splits the read line using a space as a regex
                    String numbers[] = line.split(" ");

                    // parses the integer value from the string (which is stored in the numbers array)
                    int num = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = num;
                    column++;
                }

                if (column == gamePanel.maxScreenColumns) {
                    column = 0;
                    row++;
                }
            }

            mapReader.close();

        }
            
        catch(Exception e) {
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

            int tileNum = mapTileNumber[columns][rows];

            g2.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
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