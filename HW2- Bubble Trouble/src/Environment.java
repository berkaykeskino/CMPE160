import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Environment {
    //Time variable, (40000 ms)
    public static double time;
    //canvas sizes
    public int canvasWidth = 800;
    public int canvasHeight = 500;
    //calculate how much time past
    public double start = System.currentTimeMillis();
    public double change = System.currentTimeMillis();
    //locations of the player, and its sizes
    public static double playerX = 8;
    public static double playerY = 0.9;
    public static double playerHeight = (double) 10/8;
    public static double playerWidth = (double) 27*playerHeight/37;
    // true if we can create arrow
    public static boolean arrowReady = true;
    //x coordinate of arrow
    public static double arrowLocation;
    //store all balls in an arraylist, if n th ball has "1" value in "check", it can be seen
    public static ArrayList<Ball> allBalls = new ArrayList<Ball>();
    public static ArrayList<String> check = new ArrayList<String>();
    //information about the balls we will create, {level, x coord.,y coord}
    public static double[] newBalls = new double[] { -1,-1,-1};

    // true if a ball didn't hit the player
    public static boolean noCollusion = true;
    //how many balls we hit
    public static int done = 0;
    //if the game ends, how much time we have
    public static double lastTime;
    //if the game ends, where is the arrow
    public static double arrowXLast;
    public static double arrowYlast;

    public Environment(double time){
        //ask = true means user wants to play
        boolean ask = true;
        StdDraw.enableDoubleBuffering();
        //create the canvas and set scales
        initialize();

        while (ask) {
            //create the balls at the beginning
            Ball ball = new Ball(1, 2, playerHeight * 1.4 * Math.pow(1.75, 1),'l');
            Ball ball2 = new Ball(2, 2,playerHeight * 1.4 * Math.pow(1.75, 2),'r');
            Ball ball3 = new Ball(0, 2, playerHeight * 1.4 * Math.pow(1.75, 0),'r');
            //time variable we entered in main class
            Environment.time = time;
            //if there is time and ball didnt hit us and we didnt shoot all the balls
            while (change - start < time && noCollusion && done != 11) {
                //draw the backgroung image
                StdDraw.picture(8, 4, "background.png", 16, 10);
                //throw an arrow if user wants and he can (arrow is Ready)
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && arrowReady){
                    arrowLocation = playerX;
                    arrowXLast = arrowLocation;
                    arrowReady = false;
                }
                //when the user cant throw an arrow, there is an arrow going up in Arrow class
                if (!arrowReady){
                    Arrow arrow = new Arrow(arrowLocation);
                }

                //change - start is the time passed. We use it to draw decreasing time
                createBar(change - start);
                //create the player
                Player player = new Player();

                //for all balls created
                for (Ball j : allBalls){
                    //if the ball still is in the game
                    if (check.get(j.number).equals("1")){
                        //show motion
                        j.move();
                        //check if a ball hits the player
                        j.collusion();
                        //check if arrow hits the ball
                        j.split();
                    }

                }
                //newBalls contains the info about the balls to be created
                if (newBalls[0] != -1){
                    //ball to left
                    Ball yeni = new Ball((int) newBalls[0], newBalls[1], newBalls[2], 'l');
                    //ball to right
                    Ball yeni2 = new Ball((int) newBalls[0], newBalls[1], newBalls[2], 'r');
                    newBalls[0] = -1;
                    newBalls[1] = -1;
                    newBalls[2] = -1;

                }
                ////////////////////////////
                //use this if the game ends
                lastTime = change - start;
                //use this to calculate time past
                change = System.currentTimeMillis();
                //show the image created
                StdDraw.show();
            }
            //if the game ends,
            // background
            StdDraw.picture(8, 4, "background.png", 16, 10);
            //if win, the arrow
            if (done == 11) {
                StdDraw.picture(arrowXLast,arrowYlast-5,"arrow.png");
            }
            //time bar and player
            createBar(lastTime);
            Player player = new Player();


            //if win, see "You Won!"
            if (done == 11){
                win();
            }else {//If lose, see you lost
                lose();
            }
            //if the game starts again, number of balls we hit is 0 again
            done = 0;
            StdDraw.show();
            while (true){
                //if play again, all variables are in the form they were
                if (StdDraw.isKeyPressed('Y')){
                    start = System.currentTimeMillis();
                    change = System.currentTimeMillis();
                    noCollusion = true;
                    allBalls = new ArrayList<Ball>();
                    check = new ArrayList<String>();
                    Ball.counter = 0;
                    Arrow.arrowEdgeY = Environment.playerHeight;
                    arrowReady = true;

                    break;
                } else if (StdDraw.isKeyPressed('N')) {//if not play again, finish executing
                    ask = false;
                    System.exit(1);
                    break;
                }

            }
        }



    }
    //create bar in bar class
    public void createBar(double timeDifference){
        Bar bar = new Bar(timeDifference);
    }
    public void initialize(){//create canvas
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0,16.0);
        StdDraw.setYscale(-1.0, 9.0);

    }
    public void lose(){//if lose, put game_screen to the middle and ask if play again, push y, if not n
        StdDraw.picture(8, (double) 10/2.18, "game_screen.png",(double) 16/3.18,(double) 10/4);
        StdDraw.setPenColor(0,0,0);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(8, 5, "Game Over!");
        font = new Font("Helvetica", Font.ITALIC, 15);
        StdDraw.setFont(font);
        StdDraw.text(8, (double) 10 / 2.3, "To Replay Click \"Y\"");
        StdDraw.text(8, (double) 10 / 2.6, "To Quit Click \"N\"");
    }
    public void win(){//if win, put game_screen to the middle and ask if play again, push y, if not n
        StdDraw.picture(8, (double) 10/2.18, "game_screen.png",(double) 16/3.18,(double) 10/4);
        StdDraw.setPenColor(0,0,0);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(8, 5, "You Won!");
        font = new Font("Helvetica", Font.ITALIC, 15);
        StdDraw.setFont(font);
        StdDraw.text(8, (double) 10 / 2.3, "To Replay Click \"Y\"");
        StdDraw.text(8, (double) 10 / 2.6, "To Quit Click \"N\"");
    }
}
