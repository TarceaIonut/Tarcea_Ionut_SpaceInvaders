package ro.itarcea.spaceInvaders.gui.gameElements.impl;

import ro.itarcea.spaceInvaders.gui.AllSprites;
import ro.itarcea.spaceInvaders.gui.gameElements.Invader;
import ro.itarcea.spaceInvaders.gui.gameElements.Movable;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class InvaderM1 extends Invader implements Movable {
    public InvaderM1(int locationHeight, int locationWidth) throws IOException {
        super(locationHeight, locationWidth);
    }
    @Override
    public BufferedImage getCurrentSprite() {
        if (health == 1)
            return AllSprites.explodeInvader;
        if (spriteNumber % 12 < 6)
            return AllSprites.spriteM1;
        return AllSprites.spriteM2;
    }
}
