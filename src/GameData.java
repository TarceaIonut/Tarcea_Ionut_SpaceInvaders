import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GameData {
    public int score;
    public int level;

    public int nrLives;
    public long gameTimeClock;
    public double chanceOfAttack;

    private int nextMilestone;
    private int goDownValue;
    private int speedInvaders;
    private int speedDefender;
    private int speedProjectile;
    private int nrInvaders;
    private int framesWithNoDefenderAttack;
    public int gameSpeed;
    public boolean gameStarded = false;

    public int framesAfterDeath;
    public int gameStatus;

    public char invaderDirection = 'r';
    public static int height = 224, width = 256;
    public BufferedImage fullImage;
    public Invader[][] invaders = new Invader[6][11];
    public Defender player = new Defender(224 - 12, 123);

    public Set<Entity> enemies = new HashSet<>();
    public Set<Entity> friendlies = new HashSet<>();

    public void resetForNewGame(){
        this.score = 0;
        this.nextMilestone = 1000;
        this.nrLives = 3;
    }

    public GameData() throws IOException {
        this.nextMilestone = 1000;
        this.level = 1;
        this.nrLives = 3;
        this.goDownValue = 2;
        this.gameStatus = 0;
        this.score = 0;
        this.chanceOfAttack = 0.001;
        this.speedInvaders = 1;
        this.speedDefender = 2;
        this.speedProjectile = 5;
        this.fullImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        resetForNewGame();
        setForNewCicle();
    }
    public void setForNewCicle() throws IOException {
        this.gameSpeed = 1;
        this.framesAfterDeath = 0;
        this.nrInvaders = 55;
        this.framesWithNoDefenderAttack = 100;
        this.gameStatus = 0;
        this.player.health = 2;

        friendlies.clear();
        enemies.clear();

        makeInvadersMatrix();
        makeSets();
    }
    public void makeSets() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                enemies.add(invaders[i][j]);
            }
        }
        friendlies.add(player);
    }

    public void makeInvadersMatrix() throws IOException {
        int currHeight = 20;
        int currWidth = 50;
        for (int j = 0; j < 11; j++) {
            invaders[0][j] = new InvaderS1(currHeight, currWidth + j * 15);
        }
        for (int j = 0; j < 11; j++) {
            invaders[1][j] = new InvaderM1(currHeight + 15, currWidth + j * 15);
        }
        for (int j = 0; j < 11; j++) {
            invaders[2][j] = new InvaderM1(currHeight + 30, currWidth + j * 15);
        }
        for (int j = 0; j < 11; j++) {
            invaders[3][j] = new InvaderL1(currHeight + 45, currWidth + j * 15);
        }
        for (int j = 0; j < 11; j++) {
            invaders[4][j] = new InvaderL1(currHeight + 60, currWidth + j * 15);
        }
//        for (int j = 0; j < 11; j++) {
//            invaders[5][j] = new InvaderL1(currHeight + 75, currWidth + j * 15);
//        }
    }

    public void makeNewFrame() throws IOException {
        if (this.gameStatus != 0){
            increaseAllSpriteNumber();
        }
        else{
            if (this.nrInvaders < 40)
                this.gameSpeed = 2;
            if (this.nrInvaders < 20)
                this.gameSpeed = 3;

            increaseAllSpriteNumber();
            getRidOfDeadInvaders();
            createNewProjectiles();
            moveInvaders();
            moveDefender();
            moveProjectiles();
            killEmAll();
            if (this.score >= this.nextMilestone){
                this.nrLives++;
                this.nextMilestone *= 2;
            }
        }
        if (this.nrInvaders == 0){
            this.gameStatus = 1;
        }
        if (this.gameStatus == 1 && this.framesAfterDeath == 30){
            setForNewCicle();
            this.gameStatus = 0;
            //System.out.println("score = " + this.score);
        }
        if (this.gameStatus == -1 && this.framesAfterDeath == 30 && this.nrLives > 0)
            this.nrLives--;
        if (this.gameStatus == -1 && this.nrLives > 0 && this.framesAfterDeath == 30){
            respawnPlayer();
            this.gameStatus = 0;
            this.framesAfterDeath = 0;
            //System.out.println("Lives: " + nrLives);
        }
    }
    public void respawnPlayer() {
        enemies.clear();
        friendlies.clear();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                if (invaders[i][j] != null)
                    enemies.add(invaders[i][j]);
            }
        }
        friendlies.add(player);
        player.health = 2;
        player.locationWidth = width / 2;
    }
    public void getRidOfDeadInvaders(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 11; j++) {
                if (invaders[i][j] != null){
                    if (invaders[i][j].health < 2){
                        invaders[i][j] = null;
                    }
                }
            }
        }
    }
    public void killEmAll() {
        Set<Entity> theDead = new HashSet<>();
        for (Entity e : friendlies) {
            for (Entity E : enemies) {
                if (e.intersecting(E)) {
                    theDead.add(e);
                    theDead.add(E);
                    if (e instanceof Defender) {
                        this.gameStatus = -1;
                    }
                    if (E instanceof Invader) {
                        this.nrInvaders--;
                    }
                    break;
                }
            }
        }
        //System.out.println(theDead.size());
        for (Entity e : theDead) {
            if (e instanceof InvaderL1) {
                score += 10;
            }
            if (e instanceof InvaderM1) {
                score += 20;
            }
            if (e instanceof InvaderS1) {
                score += 30;
            }
            if (e instanceof Invader) {
                ((Invader) e).health = 1;
                //System.out.println("Invader Killed");
            }
            if (e instanceof Defender) {
                ((Defender) e).health = 1;
                //System.out.println("Defender Killed");
            }
            enemies.remove(e);
            friendlies.remove(e);
        }
    }
    public void increaseAllSpriteNumber() {
        for (Entity entity : enemies) {
            entity.spriteNumber++;
            if (entity instanceof Invader) {
                entity.spriteNumber += gameSpeed - 1;
            }
        }
        for (Entity entity : friendlies) {
            entity.spriteNumber++;
        }
        if (gameStatus == -1) {
            player.spriteNumber++;
        }
    }

    public void createNewProjectiles() {
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 11; j++) {
                if (invaders[i][j] != null) {
                    int x = 0;
                    if (invaders[i][j] instanceof InvaderL1) {
                        x = 1;
                    }
                    else if (invaders[i][j] instanceof InvaderM1) {
                        x = 2;
                    }
                    else if (invaders[i][j] instanceof InvaderS1) {
                        x = 3;
                    }
                    if (((double) rand.nextInt(1000000) / 1000000) <= this.chanceOfAttack * x * gameSpeed) {
                        Entity projectile = new Projectile(invaders[i][j].getCurrentSprite().getHeight() + invaders[i][j].locationHeight,
                                invaders[i][j].locationWidth + invaders[i][j].getCurrentSprite().getWidth() / 2, 'e');
                        this.enemies.add(projectile);
                    }
                }
            }
        }
        if (player.inputFire == 'f' && this.framesWithNoDefenderAttack >= 10 && this.gameStatus != -1) {
            Entity projectile = new Projectile(player.locationHeight - player.getCurrentSprite().getHeight(),
                    player.locationWidth + player.getCurrentSprite().getWidth() / 2, 'd');
            friendlies.add(projectile);
            this.player.inputFire = 'u';
            this.framesWithNoDefenderAttack = 0;
        } else {
            this.framesWithNoDefenderAttack++;
        }
    }
    public synchronized void moveProjectiles() {
        Set<Entity> toDeleteE = new HashSet<>();
        Set<Entity> toDeleteF = new HashSet<>();
        for (Entity entity : enemies) {
            if (entity instanceof Projectile) {
                if (((Projectile) entity).collide('u', speedProjectile + gameSpeed - 1))
                    toDeleteE.add(entity);
                else {
                    if (((Projectile) entity).move('u', speedProjectile + gameSpeed - 1) == 'u')
                        toDeleteE.add(entity);
                }
            }
        }
        for (Entity entity : friendlies) {
            if (entity instanceof Projectile) {
                if (((Projectile) entity).collide('u', speedProjectile + gameSpeed - 1))
                    toDeleteF.add(entity);
                else {
                    if (((Projectile) entity).move('u', speedProjectile + gameSpeed - 1) == 'u')
                        toDeleteF.add(entity);
                }
            }
        }
        for (Entity entity : toDeleteE) {
            enemies.remove(entity);
        }
        for (Entity entity : toDeleteF) {
            friendlies.remove(entity);
        }
    }

    public void moveDefender() {
        player.move(player.inputMovement, speedDefender);
        //player.inputMovement = 'u';
    }

    public void moveInvaders() {
        boolean canGo = true;
        for (int i = 0; i < invaders.length; i++) {
            for (int j = 0; j < invaders[i].length; j++) {
                if (invaders[i][j] != null) {
                    if (invaders[i][j].collide(invaderDirection, speedInvaders + gameSpeed - 1)) {
                        canGo = false;
                    }
                }
            }
        }
        if (!canGo) {

            if (invaderDirection == 'r') invaderDirection = 'l';
            else invaderDirection = 'r';
            canGo = true;
            int maxHeightReached = 0;
            for (int i = 5; i >= 0; i--) {
                for (int j = 0; j < 11; j++) {
                    if (invaders[i][j] != null) {
                        maxHeightReached = Math.max(maxHeightReached, invaders[i][j].locationHeight);
                    }

                }
                if (maxHeightReached != 0) break;
            }
            for (int i = 0; i < invaders.length; i++) {
                for (int j = 0; j < invaders[i].length; j++) {
                    if (invaders[i][j] != null) {
                        if (maxHeightReached < 180)
                            invaders[i][j].locationHeight += goDownValue;
                        if (invaders[i][j].collide(invaderDirection, speedInvaders  + gameSpeed - 1 )) {
                            canGo = false;
                        }
                    }
                }
            }
            if (!canGo) {
                System.out.println("All Invaders Collide Problem");
            }
        }
        for (int i = 0; i < invaders.length; i++) {
            for (int j = 0; j < invaders[i].length; j++) {
                if (invaders[i][j] != null) {
                    invaders[i][j].move(invaderDirection, speedInvaders  + gameSpeed - 1);
                }
            }
        }
    }
    public void resetGame() throws IOException {
        resetForNewGame();
        setForNewCicle();
    }
}