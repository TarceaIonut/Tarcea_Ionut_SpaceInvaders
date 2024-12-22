package ro.itarcea.spaceInvaders.gui.gameElements.impl;

import ro.itarcea.spaceInvaders.control.GameData;
import ro.itarcea.spaceInvaders.gui.AllSprites;
import ro.itarcea.spaceInvaders.gui.gameElements.Entity;
import ro.itarcea.spaceInvaders.gui.gameElements.Movable;

import java.awt.image.BufferedImage;

public class Projectile extends Entity implements Movable {
    char team;
    public Projectile(int pozH, int pozW, char team) {
        super.locationHeight = pozH;
        super.locationWidth = pozW;
        this.team = team;
    }
    @Override
    public BufferedImage getCurrentSprite() {
        if (team == 'd') {
            if (super.spriteNumber % 4 == 1)
                return AllSprites.missile_1;
            if (super.spriteNumber % 4 == 2)
                return AllSprites.missile_2;
            if (super.spriteNumber % 4 == 3)
                return AllSprites.missile_3;
            if (super.spriteNumber % 4 == 0)
                return AllSprites.missile_4;
        }
        if (team == 'e') {
            if (super.spriteNumber % 4 == 1)
                return AllSprites.projectile_1;
            if (super.spriteNumber % 4 == 2)
                return AllSprites.projectile_2;
            if (super.spriteNumber % 4 == 3)
                return AllSprites.projectile_3;
            if (super.spriteNumber % 4 == 0)
                return AllSprites.projectile_4;
        }
        System.out.println("Unknown team: Problem at ro.itarcea.spaceInvadors.gui.gameElements.impl.Projectile: pozH = " + super.locationHeight + " pozW = " + super.locationWidth);
        return AllSprites.spriteDefender;
    }

    @Override
    public char move(char direction, int speed) {
        if (collide(direction, speed)) {
            this.team = 'u';
            return 'u';
        }
        if (team == 'd'){
            super.locationHeight -= speed;
            return 'd';
        }
        if (team == 'e'){
            super.locationHeight += speed;
            return 'e';
        }
        System.out.println("team not known: " + team);
        return 0;
    }
    @Override
    public boolean collide(char direction, int speed) {
        if (team == 'd' && super.locationHeight + speed < 0)
            return true;
        if (team == 'e' && super.locationHeight + speed >= GameData.height)
            return true;
        return false;
    }
}
