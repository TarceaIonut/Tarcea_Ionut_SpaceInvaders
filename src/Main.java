import service.DbService;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{
    public static void main(String[] args) throws IOException, InterruptedException, FontFormatException, SQLException, ClassNotFoundException {
        DbService service = new DbService();
        GameData gameData = new GameData();
        Display display = new Display(gameData, service);
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis() + 100;
        int T = 50;
        while (true){
            if (endTime >= startTime + T) {
                startTime = System.currentTimeMillis();
                display.drawFrame();
            }
            endTime = System.currentTimeMillis();
        }
    }
}