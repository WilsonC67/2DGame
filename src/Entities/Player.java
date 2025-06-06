package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyboardInputHandler;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyboardInputHandler keyboard;
    BufferedImage image;

    public final int screenX;
    public final int screenY;
    
    // A Game Panel and a keylistener as arguments so the gamePanel knows of the instance of the player and thus can call
    // on its methods, thus reacting to the player's key inputs
    public Player(GamePanel gamePanel, KeyboardInputHandler keyboard) {
        // using "this" keyword because the parameters for this constructor and names of the variables are the same
        this.gamePanel = gamePanel;
        this.keyboard = keyboard;

        // we get the midpoint of the map and subtract half a tileSize from both values for centering the player
        screenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        screenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

        collisionArea = new Rectangle(8, 8, 16, 16);

        setInitialValues();
        setPlayerSprites();
        
    }

    /**
     * Loads the player sprites.
     * Preconditions: all sprite pngs are valid (in the right spot) and can be scanned appropriately
     * Postconditions: all sprite pngs are stored in the 8 BufferedImage objects
     */
    public void setPlayerSprites() {

        File f1 = new File("./src/res/player/slime_up_1.png");
        File f2 = new File("./src/res/player/slime_up_2.png");
        File f3 = new File("./src/res/player/slime_down_1.png");
        File f4 = new File("./src/res/player/slime_down_2.png");
        File f5 = new File("./src/res/player/slime_left_1.png");
        File f6 = new File("./src/res/player/slime_left_2.png");
        File f7 = new File("./src/res/player/slime_right_1.png");
        File f8 = new File("./src/res/player/slime_right_2.png");

        try {
            up1 = ImageIO.read(f1);

            up2 = ImageIO.read(f2);

            down1 = ImageIO.read(f3);

            down2 = ImageIO.read(f4);

            left1 = ImageIO.read(f5);

            left2 = ImageIO.read(f6);

            right1 = ImageIO.read(f7);

            right2 = ImageIO.read(f8);

        }

        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the initial location, speed, and direction of the player
     * Preconditions: none
     * Postconditions: initial values for the location, speed, and direction of the player are set to explicit values
     */
    public void setInitialValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "left";
    }

    /**
     * Updates the location of the player using keyboard input
     * Precondition: the game panel and keyboard are both initialized via the constructor
     * Postconditions:
     *      the player/sprite is moved accordingly to the corresponding clicked key
     *      also shuffles the sprite counter to imitate movement
     */
    public void update() {
        // purpose of this if block is to make it so that the spriteCounter only increments
        // only if the user is pressing a movement key
        if (keyboard.upPressed == true || keyboard.downPressed == true || 
            keyboard.leftPressed == true || keyboard.rightPressed == true) {

            if (keyboard.upPressed == true) {
                direction = "up";
                worldY -= speed;

            } else if (keyboard.downPressed == true) {
                direction = "down";
                worldY += speed;

            } else if (keyboard.leftPressed == true) {
                direction = "left";
                worldX -= speed;

            } else if (keyboard.rightPressed == true) {
                direction = "right";
                worldX += speed;
            }

            collisionOn = false;
            // collisionChecker receives the Player object as an Entity to check it
            gamePanel.collisionChecker.checkTile(this);

            // this update method gets called 60 times per second
            // every frame, spriteCounter is incremented; when this reaches 10, it changes the sprite
            // this means that the playerSprite changes every 10 frames
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            // System.out.println(String.format("X: %d\nY:%d", playerX, playerY));
        }

    }

    /**
     * Draws the player sprite depending on its direction
     * @param g2
     * Preconditions: player, gamePanel, and keyboard are all valid
     * Postconditions: draws the player sprite with its differing movement sprites
     */
    public void draw(Graphics2D g2) {

        // g2.setColor(Color.WHITE);
        // // this will draw a rectangle with the color that was declared last; in this case: white
        // g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
        
        image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "left":
            if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
                
            case "right":
            if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}