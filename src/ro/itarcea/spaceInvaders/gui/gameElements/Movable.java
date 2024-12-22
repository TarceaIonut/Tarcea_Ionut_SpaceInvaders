package ro.itarcea.spaceInvaders.gui.gameElements;

public interface Movable {
    char move(char direction, int speed);
    boolean collide(char direction, int speed);
}
