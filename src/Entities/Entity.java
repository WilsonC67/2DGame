package Entities;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// parent class for any player classes, characters, etc
public class Entity {
    // x and y coordinates for the entities
    public int worldX, worldY;

    // how many pixels the entity travels in one frame
    public int speed;

    // 8 images to represent the entity; 2 for each cardinal direction
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    // String that contains the direction of which the entity is traveling
    public String direction;
    
    // these two int values are for fluctuating between the two images to imitate a more realistic walking movement
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // an area that is put on the player sprite to detect collision; only some part of the player sprite should
    // count for collision. this area is intuitively smaller than the size of the player sprite (48x48 px) 
    public Rectangle collisionArea;
    public boolean collisionOn = false;
}
