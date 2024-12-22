import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AllSprites {
    public static BufferedImage spriteL1;
    public static BufferedImage spriteL2;
    public static BufferedImage spriteM1;
    public static BufferedImage spriteM2;
    public static BufferedImage spriteS1;
    public static BufferedImage spriteS2;

    public static BufferedImage spriteL1_2;
    public static BufferedImage spriteL2_2;
    public static BufferedImage spriteM1_2;
    public static BufferedImage spriteM2_2;
    public static BufferedImage spriteS1_2;
    public static BufferedImage spriteS2_2;

    public static BufferedImage explodeInvader;

    public static BufferedImage spriteDefender;
    public static BufferedImage spriteExplode_1;
    public static BufferedImage spriteExplode_2;

    public static BufferedImage missile_1;
    public static BufferedImage missile_2;
    public static BufferedImage missile_3;
    public static BufferedImage missile_4;

    public static BufferedImage projectile_1;
    public static BufferedImage projectile_2;
    public static BufferedImage projectile_3;
    public static BufferedImage projectile_4;

    public static BufferedImage gameOver;
    public static BufferedImage StartGame;


     static {
        try {
            File currentDirectory = new File(".");
            spriteL1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderL1.png"));
            spriteL2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderL2.png"));
            spriteM1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderM1.png"));
            spriteM2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderM2.png"));
            spriteS1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderS1.png"));
            spriteS2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderS2.png"));

            explodeInvader = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\invaderExplosion.png"));

            spriteL1_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderL1.png"));
            spriteL2_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderL2.png"));
            spriteM1_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderM1.png"));
            spriteM2_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderM2.png"));
            spriteS1_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderS1.png"));
            spriteS2_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\InvaderS2.png"));

            spriteDefender = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\Defender.png"));
            spriteExplode_1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\playerExplosionA.png"));
            spriteExplode_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\playerExplosionB.png"));

            missile_1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\missile_1.png"));
            missile_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\missile_2.png"));
            missile_3 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\missile_3.png"));
            missile_4 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\missile_4.png"));

            projectile_1 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\ProjectileA_1.png"));
            projectile_2 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\ProjectileA_2.png"));
            projectile_3 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\ProjectileA_3.png"));
            projectile_4 = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\ProjectileA_4.png"));

            gameOver = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\GameOver.png"));
            StartGame = ImageIO.read(new File(currentDirectory.getAbsolutePath() + "\\Assets\\StartGame.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
