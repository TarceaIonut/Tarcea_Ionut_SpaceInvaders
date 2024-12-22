package ro.itarcea.spaceInvaders.gui.gameElements;

import ro.itarcea.spaceInvaders.control.GameData;

import java.io.IOException;

public abstract class Invader extends Entity implements Movable {
    public int health;

    int hSprite = 8, wSprite = 12;

    public Invader(int locationHeight, int locationWidth) throws IOException {
        this.locationHeight = locationHeight;
        this.locationWidth = locationWidth;
        this.health = 2;
        this.spriteNumber = 1;
    }
    public char move(char direction, int speed) {
        if (this.collide(direction, speed)) {
            if (direction == 'r') direction = 'l';
            else direction = 'r';
        }
        if (this.collide(direction, speed)) {
            System.out.println("Problem at ro.itarcea.spaceInvadors.gui.gameElements.Invader.Colide");
        }
        else{
            if (direction == 'l') {
                locationWidth -= speed;
            }
            if (direction == 'r') {
                locationWidth += speed;
            }
        }
        return direction;
    }
    public boolean collide(char direction, int speed) {
        if (direction == 'r') {
            if (locationWidth + speed <= GameData.width - wSprite) {
                return false;
            }
        }
        if (direction == 'l') {
            if (locationWidth - speed >= 0) {
                return false;
            }
        }
        return true;
    }
}
