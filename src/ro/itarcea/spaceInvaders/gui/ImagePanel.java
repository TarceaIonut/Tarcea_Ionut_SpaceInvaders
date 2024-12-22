package ro.itarcea.spaceInvaders.gui;

import ro.itarcea.spaceInvaders.gui.gameElements.impl.Defender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements KeyListener{
    private BufferedImage image;
    private Defender player;

    public ImagePanel(BufferedImage image, Defender player) {
        this.image = image;
        this.player = player;
        this.addKeyListener(this);
    }
    public Defender getPlayer(){
        return player;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the BufferedImage
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2; // Center the image horizontally
            int y = (getHeight() - image.getHeight()) / 2; // Center the image vertically
            g.drawImage(image, x, y, this);
        }
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("KeyTyped");
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.player.inputMovement = 'l';
        }
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.player.inputMovement = 'r';
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.player.inputFire = 'f';
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("KeyTyped");
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.player.inputMovement = 'l';
        }
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.player.inputMovement = 'r';
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.player.inputFire = 'f';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
//            this.inputMovement = 'l';
//        }
//        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            this.inputMovement = 'r';
//        }
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            this.inputFire = 'f';
//        }
    }
}