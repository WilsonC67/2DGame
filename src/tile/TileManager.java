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
        mapTileNumber = new int[gamePanel.maxWorldColumns][gamePanel.maxWorldRows];
        
        getTileImage();
        readMapFromFile("./src/res/maps/map2.txt");
    }

    /**    
     * Reads the tile pngs.
     * Pre-conditions: all tile pngs are valid and accessible
     * Post-conditions: all tile pngs are stored in the tile array
    */
    public void getTileImage() {
        File grassTile = new File("./src/res/tiles/grass.png");
        File waterTile = new File("./src/res/tiles/water.png");
        File wallTile = new File("./src/res/tiles/wall.png");
        File dirtTile = new File("./src/res/tiles/dirt.png");
        File sandTile = new File("./src/res/tiles/sand.png");
        File treeTile = new File("./src/res/tiles/tree.png");
        
        try {
            // 0 means a grass tile.
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(grassTile);

            // 1 means a wall tile.
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(wallTile);

            // 2 means a water tile.
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(waterTile);

            // 3 means a dirt tile
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(dirtTile);

            // 4 means a tree tile
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(treeTile);

            // 5 means a sand tile
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(sandTile);
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

            while (column < gamePanel.maxWorldColumns && row < gamePanel.maxWorldRows) {
                // reads a line at a time
                String line = mapReader.readLine();

                while (column < gamePanel.maxWorldColumns) {
                    // this splits the read line using a space as a regex
                    String numbers[] = line.split(" ");

                    // parses the integer value from the string (which is stored in the numbers array)
                    int num = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = num;
                    column++;
                }

                if (column == gamePanel.maxWorldColumns) {
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

        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gamePanel.maxWorldColumns && worldRow < gamePanel.maxWorldRows) {

            int tileNum = mapTileNumber[worldColumn][worldRow];

            int worldX = worldColumn * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // setting a boundary so that it will only render the tiles around the player instead of
            // drawing the entire map constantly
            if (worldX + gamePanel.tileSize > (gamePanel.player.worldX - gamePanel.player.screenX + 1) && 
                worldX - gamePanel.tileSize < (gamePanel.player.worldX + gamePanel.player.screenX + 1) && 
                worldY + gamePanel.tileSize > (gamePanel.player.worldY - gamePanel.player.screenY + 1) && 
                worldY - gamePanel.tileSize < (gamePanel.player.worldY + gamePanel.player.screenY + 1)) {
                
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

                }

            worldColumn++;

            if (worldColumn == gamePanel.maxWorldColumns) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }
}