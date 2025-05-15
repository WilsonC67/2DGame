package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
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

    public Player(GamePanel gamePanel, KeyboardInputHandler keyboard) {
        // using "this" keyword because the parameters for this constructor and names of the variables are the same
        this.gamePanel = gamePanel;
        this.keyboard = keyboard;


        setDefaultValues();
        // getPlayerImage(); // keyboard isn't receiving feedback?
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "left";
    }

    public void getPlayerImage() {

        try {

            File f1 = new File("./src/res/player/slime_up_1.png");
            up1 = ImageIO.read(f1);

            File f2 = new File("./src/res/player/slime_up_2.png");
            up2 = ImageIO.read(f2);

            File f3 = new File("./src/res/player/slime_down_1.png");
            down1 = ImageIO.read(f3);

            File f4 = new File("./src/res/player/slime_down_2.png");
            down2 = ImageIO.read(f4);

            File f5 = new File("./src/res/player/slime_left_1.png");
            left1 = ImageIO.read(f5);

            File f6 = new File("./src/res/player/slime_left_2.png");
            left2 = ImageIO.read(f6);

            File f7 = new File("./src/res/player/slime_right_1.png");
            right1 = ImageIO.read(f7);

            File f8 = new File("./src/res/player/slime_right_2.png");
            right2 = ImageIO.read(f8);

        }

        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        System.out.println(String.format("%d %d %d", x, y, speed));

        System.out.println(keyboard.upPressed);
        System.out.println(keyboard.leftPressed);
        System.out.println(keyboard.rightPressed);
        System.out.println(keyboard.downPressed);


        if (keyboard.upPressed == true) {
            direction = "up";
            y -= speed;

        } else if (keyboard.downPressed == true) {
            direction = "down";
            y += speed;

        } else if (keyboard.leftPressed == true) {
            direction = "left";
            x -= speed;

        } else if (keyboard.rightPressed == true) {
            direction = "right";
            x += speed;
        }

        // System.out.println(String.format("X: %d\nY:%d", playerX, playerY));
    }

    public void draw(Graphics2D g2) {

        // File f1 = new File("./src/res/player/slime_up_1.png");
        // try {
        //     up1 = ImageIO.read(f1);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // image = up1;

        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

        // g2.setColor(Color.WHITE);
        // // this will draw a rectangle with the color that was declared last; in this case: white
        // g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        image = up1;

        switch(direction) {
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }
        // g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}