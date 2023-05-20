import java.awt.event.KeyEvent;

public class Player {
    public static double playerSpeed = 0.05;
    public Player(){
        //draw player
        StdDraw.picture(Environment.playerX,Environment.playerY,"player_back.png",Environment.playerWidth,Environment.playerHeight);
        movePlayer();
    }
    public void movePlayer(){
        //if press right goes to right, vice versa
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && (Environment.playerX +  Environment.playerWidth/2) < 16){
            Environment.playerX += playerSpeed;
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && (Environment.playerX - Environment.playerWidth/2)>0) {
            Environment.playerX -= playerSpeed;
        }

    }
}
