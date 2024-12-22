import service.DbService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Display extends JFrame implements ActionListener,GameComponent{

    public BufferedImage fullResolutionImage = new BufferedImage(256*4, 224*4, BufferedImage.TYPE_INT_RGB);
    private String user;
    private final DbService service;
    ImagePanel panelGame;
    JLabel scoreText = new JLabel("");
    JLabel hiScoreText = new JLabel("");
    JLabel livesText = new JLabel("");
    JLabel userLabel = new JLabel("User");
    JLabel thisUserLabel = new JLabel("Unsigned");
    static Font pixelFontNotSet, pixelFont;

    JButton signUpButton = new JButton("Sign Up");
    JButton signInButton = new JButton("Sign In");
    JButton signPlayButton = new JButton("Start Game");
    GameData gameData;
    PopupFactory pf;
    private int scaleFactor;
    private int HiScore = 0;
    public boolean gamePaused = true;
    private boolean showStart;
    public boolean buttonPressed = false;

    public Display(GameData gameData, DbService service) throws IOException, FontFormatException {
        try{
            fullResolutionImage = new BufferedImage(gameData.fullImage.getWidth() * scaleFactor, gameData.fullImage.getHeight() * scaleFactor, BufferedImage.TYPE_INT_ARGB);
        }
        catch(Exception e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.setUser("");
        this.service = service;
        this.pf = new PopupFactory();
        this.showStart = true;
        this.gameData = gameData;
        pixelFontNotSet = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Ionut\\IdeaProjects\\SpaceInvaders\\Assets\\HomeVideo-BLG6G.ttf"));
        pixelFont = pixelFontNotSet.deriveFont(40f);
        this.scaleFactor = 4;
        this.setTitle("Display");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setPreferredSize(new Dimension( 260 * scaleFactor, 224 * scaleFactor));
        this.setLayout(new BorderLayout(0, 0));
        this.setVisible(true);
        // = new ImagePanel(new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB), new Defender(0, 0));
        panelGame = new ImagePanel(new BufferedImage(GameData.width * scaleFactor,GameData.height * scaleFactor, BufferedImage.TYPE_INT_RGB), gameData.player);
        panelGame.setPreferredSize(new Dimension( GameData.width * scaleFactor, GameData.height * scaleFactor));

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2,4));
        JLabel scoreLabel = new JLabel("SCORE");
        JLabel hiscoreLabel = new JLabel("HI-SCORE");
        JLabel livesLabel = new JLabel("LIVES");

        setDesign(signInButton);
        setDesign(signPlayButton);
        setDesign(signUpButton);
        setDesign(scoreLabel);
        setDesign(hiscoreLabel);
        setDesign(livesLabel);
        setDesign(thisUserLabel);
        setDesign(userLabel);
        setDesign(scoreText);
        setDesign(hiScoreText);
        setDesign(livesText);

        scorePanel.add(userLabel);
        scorePanel.add(scoreLabel);
        scorePanel.add(hiscoreLabel);
        scorePanel.add(livesLabel);
        scorePanel.add(thisUserLabel);
        scorePanel.add(scoreText);
        scorePanel.add(hiScoreText);
        scorePanel.add(livesText);

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1,3));

        signUpButton.addActionListener(this);
        signInButton.addActionListener(this);
        signPlayButton.addActionListener(this);
        menu.add(signUpButton);
        menu.add(signInButton);
        menu.add(signPlayButton);

        this.add(menu, BorderLayout.NORTH);
        this.add(panelGame, BorderLayout.CENTER);
        this.add(scorePanel, BorderLayout.SOUTH);

        panelGame.setFocusable(true);
        this.pack();
    }
    public void drawFrame() throws IOException, SQLException {
        if (this.buttonPressed)
            return;
        if (this.showStart){
            setUserLabel(this.thisUserLabel);
            setHiScoreLabel(this.hiScoreText);
            showGameStartScreen();
            panelGame.setImage(fullResolutionImage);
            scoreText.setText(String.valueOf(gameData.score));
            livesText.setText(String.valueOf(gameData.nrLives));
            panelGame.revalidate();
            panelGame.repaint();
            this.setVisible(true);
            return;
        }
        if (this.gamePaused){
            setUserLabel(this.thisUserLabel);
            setHiScoreLabel(this.hiScoreText);
            scoreText.setText(String.valueOf(gameData.score));
            livesText.setText(String.valueOf(gameData.nrLives));
            panelGame.revalidate();
            panelGame.repaint();
            this.setVisible(true);
            return;
        }
        gameData.makeNewFrame();
        if (gameData.gameStatus == -1){
            if (gameData.framesAfterDeath < 30)
                gameData.framesAfterDeath++;
            else if (gameData.nrLives == 0){
                showGameOverScreen();
                panelGame.setImage(fullResolutionImage);
                gameData.gameStarded = false;
                this.gamePaused = true;
                this.signPlayButton.setText("Play Game");
            }
        }
        if (gameData.gameStatus == 1){
            if (gameData.framesAfterDeath < 30)
                gameData.framesAfterDeath++;
            drawAllOnImage();
            panelGame.setImage(fullResolutionImage);
        }
        if (gameData.gameStatus == 0 || (gameData.gameStatus == -1 && gameData.framesAfterDeath < 30)){
            drawAllOnImage();
            panelGame.setImage(fullResolutionImage);
        }
        scoreText.setText(String.valueOf(gameData.score));
        livesText.setText(String.valueOf(gameData.nrLives));
        panelGame.revalidate();
        panelGame.repaint();
        try {
            setUserLabel(thisUserLabel);
            setHiScoreLabel(hiScoreText);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.setVisible(true);
    }
    public void drawAllOnImage(){
        Graphics2D g = fullResolutionImage.createGraphics();
        //clearScreen(fullResolutionImage, fullResolutionImage.getHeight(), fullResolutionImage.getWidth());
        g.setColor(Color.black);
        g.fillRect(0, 0, fullResolutionImage.getWidth(), fullResolutionImage.getHeight());
        for (int i = 0; i < gameData.invaders.length; i++){
            for (int j = 0; j < gameData.invaders[i].length; j++){
                if (gameData.invaders[i][j] != null){
                    //System.out.println(" i = " + i + " j = " + j + gameData.invaders[i][j].getClass().getSimpleName());
                    gameData.invaders[i][j].drawSelf(g, fullResolutionImage, scaleFactor);
                }
            }
        }
        gameData.player.drawSelf(g, fullResolutionImage, scaleFactor);
        gameData.player.drawSelf(g, fullResolutionImage, scaleFactor);
        for (Entity entity : gameData.enemies){
            if (entity instanceof Projectile)
                entity.drawSelf(g, fullResolutionImage, scaleFactor);
        }
        for (Entity entity : gameData.friendlies){
            if (entity instanceof Projectile)
                entity.drawSelf(g, fullResolutionImage, scaleFactor);
        }
    }
    public void showGameOverScreen() throws SQLException {
        try{
            service.insertNewGame(this.thisUserLabel.getText(), gameData.score);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Graphics2D g = fullResolutionImage.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, fullResolutionImage.getWidth(), fullResolutionImage.getHeight());
        int x = AllSprites.gameOver.getHeight(), y = AllSprites.gameOver.getWidth();
        for (int i = 0 ; i < x; i++){
            for (int j = 0 ; j < y; j++){
                try{
                    g.setColor(new Color(AllSprites.gameOver.getRGB(i, j)));
                    g.fillRect(i * scaleFactor, j * scaleFactor, scaleFactor, scaleFactor);
                }
                catch (Exception e){
                }
            }
        }
        gameData.player.drawSelf(g, fullResolutionImage, scaleFactor);
        gameData.gameStarded = false;
    }
    public void showGameStartScreen(){
        Graphics2D g = fullResolutionImage.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, fullResolutionImage.getWidth(), fullResolutionImage.getHeight());
        int x = AllSprites.StartGame.getHeight(), y = AllSprites.StartGame.getWidth();
        for (int i = 0 ; i < x; i++){
            for (int j = 0 ; j < y; j++){
                try{
                    g.setColor(new Color(AllSprites.StartGame.getRGB(j, i)));
                    g.fillRect(j * scaleFactor, i * scaleFactor, scaleFactor, scaleFactor);
                }
                catch (Exception e){
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.signUpButton){
            this.buttonPressed = true;
            SignUpDialog p = new SignUpDialog("Sigh Up", this, service);
            pauseGame();
            try {
                gameData.resetGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == this.signInButton){
            this.buttonPressed = true;
            SignInDialog p = new SignInDialog("Sigh In", this, service);
            pauseGame();
            try {
                gameData.resetGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == this.signPlayButton){
            this.showStart = false;
            panelGame.requestFocus();
            if (!this.gameData.gameStarded){
                startGame();
                return;
            }
            if (this.gamePaused){
                unpauseGame();
                return;
            }
            pauseGame();
        }
    }
    public void pauseGame(){
        if (this.gameData.gameStarded)
            this.signPlayButton.setText("Unpause Game");
        this.gamePaused = true;

    }
    public void unpauseGame(){
        this.signPlayButton.setText("Pause Game");
        this.gamePaused = false;
    }
    public void startGame(){
        gameData.resetForNewGame();
        panelGame.requestFocus();
        this.gameData.gameStarded = true;
        this.gamePaused = false;
        this.signPlayButton.setText("Pause Game");
        try {
            this.gameData.setForNewCicle();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void setUserLabel(JLabel label) {
        if (this.userLabel.equals("")){
            label.setText("Unsigned");
        }
        else {
            label.setText(user);
        }
    }
    public void setHiScoreLabel(JLabel label) throws SQLException {
        if (this.userLabel.equals("")){
            label.setText("");
        }
        else {
            List<Integer> result = service.topScoresFromUser(this.thisUserLabel.getText());
            if (result.isEmpty()){
                label.setText("0");
                return;
            }
            label.setText(String.valueOf(result.get(0)));
        }
    }
}