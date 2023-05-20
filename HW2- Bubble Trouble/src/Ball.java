public class Ball {
    //how fast balls fastens
    public static double gravity = 3 * Math.pow(10,-3);
    //initial speed if a ball is created
    public double speed = 0.1;
    //position X if a ball is created
    public double positionX;
    //position Y if a ball is created
    public double positionY;
    //radius calculated from level
    public double radius;
    //ball goes to left or right
    public char direction;
    //speed in x coordinate
    public double speedX = 0.04;
    //how big the ball is, and its height
    public int level;
    //speed if the ball hits the ground
    public double initialSpeed;
    //check the number of the ball, for ex. if a ball splits, the new two will be 4 th and 5 th
    public static int counter = 0;
    public int number;
    public Ball(int level, double positionX, double positionY, char direction){
        this.number = counter;
        counter += 1;
        this.level = level;
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = 0.175 * Math.pow(2,level+1);
        this.direction = direction;
        //v^2 = 2ax
        this.initialSpeed = Math.pow(2*gravity*(Environment.playerHeight*1.4*Math.pow(1.75,level)),(double) 1/2);
        if (direction == 'l'){
            speedX = -speedX;
        }
        //every new ball is in the arraylist
        Environment.allBalls.add(this);
        Environment.check.add("1");


    }
    public void move(){
        //movement in x coordinate
        StdDraw.picture(positionX, positionY, "ball.png", radius, radius);
        if (positionX >= 16 - radius/2 || positionX<= radius/2){// if the ball hits a wall, direction changes
            speedX = -speedX;
        }
        positionX += speedX;


        //movement in y coordinate
        speed -= gravity;
        positionY += speed;
        if (positionY - radius/2 <= 0.3){//if hits the ground
            speed = initialSpeed;
        }
    }
    public void split(){
        //ball is low enough to hit arrow and x coordinates are similar
        if (positionX+radius/2 > Arrow.arrowEdgeX && positionX-radius/2 < Arrow.arrowEdgeX && positionY - radius/2 < Arrow.arrowEdgeY){
            if (level == 0){
                Environment.check.set(number,"0");
            }

            else if (level == 1) {
                Environment.newBalls[0] = 0;
                Environment.newBalls[1] = positionX;
                Environment.newBalls[2] = positionY;
                Environment.check.set(number,"0");
            } else if (level == 2) {
                Environment.newBalls[0] = 1;
                Environment.newBalls[1] = positionX;
                Environment.newBalls[2] = positionY;
                Environment.check.set(number,"0");
            }
            Environment.arrowYlast = Arrow.arrowEdgeY;
            Environment.done += 1;
            Environment.arrowReady = true;
            Arrow.arrowEdgeX = -2;
            Arrow.arrowEdgeY = Environment.playerHeight;
        }
    }
    public void collusion(){
        //ball is low enough to hit player and x coordinates are similar
        if ((Math.abs(positionX - Environment.playerX) < Environment.playerWidth/2+radius/2) &&  positionY - radius/2 < Environment.playerHeight){
            Environment.noCollusion = false;
        }
    }
}
