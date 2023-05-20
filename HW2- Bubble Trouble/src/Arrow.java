public class Arrow {
    //variables are static since we have only one arrow
    //initial values are assigned not to see when we didnt throw an arrow
    public static double arrowEdgeX = -2;
    public static double arrowEdgeY = Environment.playerHeight;
    public static double arrowSpeed = 0.1;
    public Arrow(double position){
        Arrow.arrowEdgeX = position;
        StdDraw.picture(arrowEdgeX,arrowEdgeY-5,"arrow.png");
        arrowEdgeY += arrowSpeed;
        if (arrowEdgeY > 9){
            //if reaches the top, we can throw again
            Environment.arrowReady = true;
            arrowEdgeX = -2;
            arrowEdgeY = Environment.playerHeight;
        }

    }

}
