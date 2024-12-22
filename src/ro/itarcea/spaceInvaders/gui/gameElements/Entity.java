package ro.itarcea.spaceInvaders.gui.gameElements;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int locationHeight = 0, locationWidth = 0;
    public int spriteNumber = 1;
    public void drawSelf(BufferedImage fullImage){
        int h = getCurrentSprite().getHeight();
        int w = getCurrentSprite().getWidth();
        //for (int i = locationHeight + h - 1; i >= locationHeight; i--) {
        for (int i = locationHeight; i < locationHeight + h; i++){
            for (int j = locationWidth ; j <= locationWidth + w - 1; j++) {
                try{
                    getCurrentSprite().getRGB(j - locationWidth, i - locationHeight);
                    fullImage.setRGB(j, i, getCurrentSprite().getRGB(j - locationWidth, i - locationHeight));
                }
                catch(Exception e){
                    //System.out.println(e.getMessage());
                    //System.out.println("Sprite" + "i = " + i + ", j = " + j + ", locationHeight = " + (j - locationWidth) + ", locationWidth = " + (i - locationHeight));
                    //System.out.println("H = " + getCurrentSprite().getHeight() + " W = " + getCurrentSprite().getWidth());
                }
            }
        }
    }
    public void drawSelf(Graphics2D g, BufferedImage fullImage, int scaleFactor){
        int h = getCurrentSprite().getHeight();
        int w = getCurrentSprite().getWidth();
        //for (int i = locationHeight + h - 1; i >= locationHeight; i--) {
        Color c;
        for (int i = locationHeight; i < locationHeight + h; i++){
            for (int j = locationWidth ; j <= locationWidth + w - 1; j++) {
                try{
                    //getCurrentSprite().getRGB(j - locationWidth, i - locationHeight);
                    //System.out.println();
                    g.setColor(new Color(getCurrentSprite().getRGB(j - locationWidth, i - locationHeight)));
                    g.fillRect(j * scaleFactor, i * scaleFactor, scaleFactor, scaleFactor);
                    //fullImage.setRGB(j, i, getCurrentSprite().getRGB(j - locationWidth, i - locationHeight));

                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    //System.out.println("Sprite" + "i = " + i + ", j = " + j + ", locationHeight = " + (j - locationWidth) + ", locationWidth = " + (i - locationHeight));
                    //System.out.println("H = " + getCurrentSprite().getHeight() + " W = " + getCurrentSprite().getWidth());
                }
            }
        }
    }
    public abstract BufferedImage getCurrentSprite();

    public boolean intersecting(Object object){
        if(!(object instanceof Entity)) {
            return false;
        }
        Entity e = (Entity) object;
        int nrHeight = getCurrentSprite().getHeight();
        int nrWidth = getCurrentSprite().getWidth();
        int nrEHeight = e.getCurrentSprite().getHeight();
        int nrEWidth = e.getCurrentSprite().getHeight();
        if (pointInSquare(locationHeight, locationWidth, nrHeight, nrWidth, e.locationHeight, e.locationWidth)) return true;
        if (pointInSquare(locationHeight, locationWidth, nrHeight, nrWidth, e.locationHeight + nrEHeight - 1, e.locationWidth)) return true;
        if (pointInSquare(locationHeight, locationWidth, nrHeight, nrWidth, e.locationHeight, e.locationWidth + nrEWidth - 1)) return true;
        if (pointInSquare(locationHeight, locationWidth, nrHeight, nrWidth, e.locationHeight + nrEHeight - 1, e.locationWidth + nrEWidth - 1)) return true;

        if (pointInSquare(e.locationHeight, e.locationWidth, nrEHeight, nrEWidth, locationHeight, locationWidth)) return true;
        if (pointInSquare(e.locationHeight, e.locationWidth, nrEHeight, nrEWidth, locationHeight + nrHeight - 1, locationWidth)) return true;
        if (pointInSquare(e.locationHeight, e.locationWidth, nrEHeight, nrEWidth, locationHeight, locationWidth + nrWidth - 1)) return true;
        if (pointInSquare(e.locationHeight, e.locationWidth, nrEHeight, nrEWidth, locationHeight + nrHeight - 1, locationWidth + nrWidth - 1)) return true;

        return false;
    }
    private boolean pointInSquare(int pozH, int pozW, int nrH, int nrW, int locH, int locW){
        if (locH >= pozH && locH < pozH + nrH && locW >= pozW && locW < pozW + nrW){
            //System.out.println(pozH + " " + pozW + " " + nrH + " " + nrW + " " + locH + " " + locW);
        }
        //System.out.println(pozH + " " + pozW + " " + nrH + " " + nrW + " " + locH + " " + locW);
        return locH >= pozH && locH < pozH + nrH && locW >= pozW && locW < pozW + nrW;
    }
}
