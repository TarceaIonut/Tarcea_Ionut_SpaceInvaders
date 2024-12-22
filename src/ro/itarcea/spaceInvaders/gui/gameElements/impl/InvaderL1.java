package ro.itarcea.spaceInvaders.gui.gameElements.impl;

import ro.itarcea.spaceInvaders.gui.AllSprites;
import ro.itarcea.spaceInvaders.gui.gameElements.Invader;
import ro.itarcea.spaceInvaders.gui.gameElements.Movable;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class InvaderL1 extends Invader implements Movable {
    public InvaderL1(int locationHeight, int locationWidth) throws IOException {
        super(locationHeight, locationWidth);
    }

    @Override
    public BufferedImage getCurrentSprite() {
        if (health == 1)
            return AllSprites.explodeInvader;
        if (super.spriteNumber % 12 < 6)
            return AllSprites.spriteL1;
        return AllSprites.spriteL2;
    }

}
