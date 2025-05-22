package Entities;
import java.awt.image.BufferedImage;

// parent class for any player classes, characters, etc
public class Entity {
    // x and y coordinates for the entities
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
