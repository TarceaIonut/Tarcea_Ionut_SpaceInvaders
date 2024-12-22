package ro.itarcea.spaceInvaders.gui.gameElements.impl;

import ro.itarcea.spaceInvaders.control.GameData;
import ro.itarcea.spaceInvaders.gui.AllSprites;
import ro.itarcea.spaceInvaders.gui.gameElements.Entity;
import ro.itarcea.spaceInvaders.gui.gameElements.Movable;

import java.awt.image.BufferedImage;

public class Defender extends Entity implements Movable {
    public int health = 2;
    public char inputMovement = 'u';
    public char inputFire = 'u';

    public Defender(int pozH, int pozW){
        this.locationHeight = pozH;
        this.locationWidth = pozW;
    }
    @Override
    public char move(char direction, int speed) {
        if (collide(direction, speed)) {
            if (direction == 'l')
                locationWidth = 0;
            if (direction == 'r')
                locationWidth = GameData.width - this.getCurrentSprite().getWidth();
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

    @Override
    public BufferedImage getCurrentSprite() {
        if (health == 2){
            return AllSprites.spriteDefender;
        }
        if (health == 1){
            if (super.spriteNumber % 6 == 0 || super.spriteNumber % 6 == 1 || super.spriteNumber % 6 == 2){
                return AllSprites.spriteExplode_1;
            }
            else return AllSprites.spriteExplode_2;
        }
        return AllSprites.spriteDefender;
    }

    @Override
    public boolean collide(char direction, int speed) {
        if (direction == 'l') {
            if (locationWidth - speed < 0) {
                return true;
            }
        }
        if (direction == 'r') {
            if (locationWidth + speed + this.getCurrentSprite().getWidth() - 1 >= GameData.width) {
                return true;
            }
        }
        return false;
    }


}
