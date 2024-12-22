public interface Movable {
    char move(char direction, int speed);
    boolean collide(char direction, int speed);
}
