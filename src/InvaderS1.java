import java.awt.image.BufferedImage;
import java.io.IOException;

public class InvaderS1 extends Invader implements Movable {
    public InvaderS1(int locationHeight, int locationWidth) throws IOException {
        super(locationHeight, locationWidth);
    }
    @Override
    public BufferedImage getCurrentSprite() {
        if (health == 1)
            return AllSprites.explodeInvader;
        if (spriteNumber % 12 < 6)
            return AllSprites.spriteS1;
        return AllSprites.spriteS2;
    }
}
