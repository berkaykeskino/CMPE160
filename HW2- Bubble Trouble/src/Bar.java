public class Bar {
    public Bar(double timeDifference){
        initialize();
        time(timeDifference);
    }
    public void initialize(){
        StdDraw.picture(8, -0.35, "bar.png");


    }
    public void time(double timeDifference){//the ratio helps to calculate the colour and the size
        StdDraw.setPenColor(255, (int) (255*(Environment.time - timeDifference)/Environment.time),0);
        StdDraw.filledRectangle(8*(Environment.time - timeDifference)/Environment.time,-0.35,8*(Environment.time - timeDifference)/Environment.time,0.35);
    }
}
