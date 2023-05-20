// Muhammet Berkay Keskin   2021400249   21.03.2023

/*
First I extracted the information in the coordinates file.
I stored the colors of the lines, names and the coordinates of the stations.
Then I asked the user from where to where he/she wants to go.
If the destination is not in the names' list, "no such station" error.
Then I call my recursive function which determines the path.
If these two stations are defined and the path's length is zero, that means they are not connected.
Else, I started creating the canvas and started animating.
 */


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Muhammet_Berkay_Keskin {
    public static void main(String[] args) throws FileNotFoundException {

        //Check if there is a file named "coordinates.txt"
        //If there is, go on. Else, exit the program.
        String fileName = "coordinates.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.printf("%s can not be found.", fileName);
            System.exit(1); // exit the program
        }

        //inputFile is our reference to the coordinates file.
        Scanner inputFile = new Scanner(file);

        //This is a nested array to hold colors of the lines. 10 lines and 3 RGB values
        //I used array because I know that there will be 10 lines.
        double[][] colorsOfLines = new double[10][3];

        //stations is a nested arraylist to hold names of the stations
        //This could also be an arraylist in an array with length 10. I preferred using arraylist to deal with the problem easily.
        ArrayList<ArrayList<String>> stations = new ArrayList<ArrayList<String>>();
        //stations is a nested arraylist to hold names of the stations. It seems like < <[10,20],[40,60]...>,...>
        //This could also be arraylist in an array with length 10.
        ArrayList<ArrayList<Integer[]>> stationLocations = new ArrayList<ArrayList<Integer[]>>();

        //Here I read the file
        int counter = 0;
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] myList = line.split(" ");

            //If this is a metro line
            if (myList.length<=2){

                //Put colors of the lines into colorsOfLines
                int counter2 = 0;
                for (String i : myList[1].split(",")){
                    colorsOfLines[counter][counter2] = Double.parseDouble(i)/255;
                    counter2++;
                }
                counter++;

                //Get the names and the locations of the stations
                ArrayList<String> stationName = new ArrayList<String>();
                ArrayList<Integer[]> stationLocation = new ArrayList<Integer[]>();
                String newline = inputFile.nextLine();
                String[] durakListesi = newline.split(" ");
                for (int i = 0; i<durakListesi.length; i++) {
                    if (i % 2 == 0) { // for example "Tuzla 45,55". When I split this, the zeroth element is the name of the station
                        stationName.add(durakListesi[i]);
                    } else {//And the First element is 45,55. So I split it again
                        String[] ekle = durakListesi[i].split(",");
                        stationLocation.add(new Integer[]{Integer.valueOf(ekle[0]), Integer.valueOf(ekle[1])});
                    }
                }
                stations.add(stationName);
                stationLocations.add(stationLocation);


            }
        }
        //We are done with the coordinates file. Close it
        inputFile.close();

        //Where do we want to go?
        Scanner scanner = new Scanner(System.in);
        String from = scanner.nextLine();
        String to = scanner.nextLine();
        //I will use these variables to check if stations are defined
        boolean definedFrom = false;
        boolean definedTo = false;
        for (ArrayList<String> i : stations){
            for (String j : i){
                //If the first station is defined
                if (from.equals(j) || from.equals(j.substring(1))){
                    definedFrom = true;
                }
                //If the second station is defined
                if (to.equals(j) || to.equals(j.substring(1))){
                    definedTo = true;
                }
            }
        }
        //If they are both defined, they will both true, else we give the error
        if (!(definedFrom  && definedTo)){
            System.out.println("No such station names in this map");
            System.exit(1);
        }

        //Are the stations connected?
        ArrayList<String> answer = new ArrayList<String>();
        //copy is a nested arraylist. If there is a 1 at index 3,5  ; that means we have visited the station stations[3][5]
        //If there is a 0, we haven't visited it yet
        ArrayList<ArrayList<String>> copy = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> station : stations) {
            ArrayList<String> addToCopy = new ArrayList<String>();
            for (int j = 0; j < station.size(); j++) {
                addToCopy.add("0");
            }
            copy.add(addToCopy);
        }

        //This will hold the stations we passed
        ArrayList<String> yol = new ArrayList<String>();
        //This will hold the locations of those stations in the function
        ArrayList<Integer[]> locations = new ArrayList<Integer[]>();
        //This will be the copy of it once we find the path
        ArrayList<Integer[]> answerLocations = new ArrayList<Integer[]>();
        findThePath(answerLocations ,answer , copy, stations, from, to, yol, stationLocations, locations);

        //If the length of the path is 0, that means we did not find a path. So there is no path
        if (answerLocations.size() == 0){
            System.out.println("These two stations are not connected");
            System.exit(1);
        }

        //Create the canvas
        int canvas_width = 1024;
        int canvas_height = 482;
        StdDraw.setCanvasSize(canvas_width, canvas_height);
        StdDraw.setXscale(0, 1024);
        StdDraw.setYscale(0, 482);


        //Write the path station by station
        for (String i : answer){
            System.out.println(i);
        }

        //Start animating
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i<answerLocations.size(); i++){

            //Put the image that we are given to the background
            StdDraw.picture(512, 241, "background.jpg");


            //First, draw the lines
            StdDraw.setPenRadius(0.012);
            for (int a = 0; a<10 ; a++){ //Take the n th line
                Color myColor = new Color((float) colorsOfLines[a][0],(float)colorsOfLines[a][1],(float)colorsOfLines[a][2]);
                StdDraw.setPenColor(myColor);

                for (int j = 1; j<stations.get(a).size();j++){//Connect n th line's stations
                    StdDraw.line(stationLocations.get(a).get(j)[0],stationLocations.get(a).get(j)[1],stationLocations.get(a).get(j-1)[0],stationLocations.get(a).get(j-1)[1]);
                }
            }

            //Put the circles
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setPenRadius(0.01);
            for (int a = 0; a<10 ; a++){//Take the n th line
                for (int j = 0; j<stations.get(a).size();j++){//Draw n th line's stations
                    StdDraw.filledCircle(stationLocations.get(a).get(j)[0],stationLocations.get(a).get(j)[1],3);
                }
            }

            //Write names of the stations
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Helvetica",Font.BOLD,8));
            for (int a = 0; a<10 ; a++){//Take the n th line
                for (int j = 0; j<stations.get(a).size();j++){//Write n th line's stations' names
                    if (stations.get(a).get(j).charAt(0) == '*'){//If it starts with "*", which we are supposed to do so
                        StdDraw.textLeft(stationLocations.get(a).get(j)[0],stationLocations.get(a).get(j)[1]+5,stations.get(a).get(j).substring(1));
                    }
                }
            }


            //Put the circles to the stations that we visited
            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            StdDraw.filledCircle(answerLocations.get(i)[0],answerLocations.get(i)[1],5);
            for (int j = 0; j<i;j++){
                StdDraw.filledCircle(answerLocations.get(j)[0],answerLocations.get(j)[1],3);
            }
            StdDraw.pause(300);
            StdDraw.show();

        }



    }
    public static void findThePath(ArrayList<Integer[]> answerLocations , ArrayList<String> answerNames , ArrayList<ArrayList<String>> copy, ArrayList<ArrayList<String>> stations, String from, String to, ArrayList<String> path, ArrayList<ArrayList<Integer[]>> stationLocations, ArrayList<Integer[]> locations){
        //If I reach to where I want to go
        if (from.equals(to) || from.equals(to.substring(1)) || to.equals(from.substring(1))){
            //Add my final location's name to path
            path.add(to);
            //Add my final location's coordinates to locations

            for (int i = 0; i<stations.size();i++) {
                for (int j = 0; j < stations.get(i).size(); j++) {
                    if (from.equals(stations.get(i).get(j))){
                        locations.add(stationLocations.get(i).get(j));
                        break;
                    }
                }
            }

            //Draw the circles using the locations in locations
            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            StdDraw.setPenRadius(0.2);


            //Write the names of the stations we passed
            for (String i : path) {

                if (i.charAt(0) == '*'){
                    answerNames.add(i.substring(1));
                }else{
                    answerNames.add(i);
                }
            }
            for (Integer[] i : locations){
                answerLocations.add(i);
            }
            return;
        }
        for (int i = 0; i<stations.size();i++){
            for (int j = 0; j<stations.get(i).size(); j++){
                // If I find my location from the list of lines
                if (from.equals(stations.get(i).get(j)) || from.substring(1).equals(stations.get(i).get(j)) || stations.get(i).get(j).substring(1).equals(from)){
                    //I visited my location, so mark it as "1" (visited)
                    copy.get(i).set(j, "1");

                    // If i am in the middle of a list
                    if (0<j && j<stations.get(i).size()-1){
                        //If I didn't visit my right
                        if (copy.get(i).get(j+1).equals("0")){
                            // Add my location to path
                            path.add(stations.get(i).get(j));
                            locations.add(stationLocations.get(i).get(j));
                            // Go to right
                            findThePath( answerLocations ,answerNames,copy, stations, stations.get(i).get(j+1), to, path, stationLocations, locations);
                            //Remove my location from path, because if I come here, it means the path I used is not the one that I am looking for
                            path.remove(path.size()-1);
                            locations.remove(locations.size()-1);
                        }
                        //If I didn't visit my left
                        if (copy.get(i).get(j-1).equals("0")){
                            // Add my location to path
                            path.add(stations.get(i).get(j));
                            locations.add(stationLocations.get(i).get(j));
                            // Go to left
                            findThePath(answerLocations, answerNames, copy, stations, stations.get(i).get(j-1), to, path, stationLocations, locations);

                            //Remove my location from path, because if I come here, it means the path I used is not the one that I am looking for
                            path.remove(path.size()-1);
                            locations.remove(locations.size()-1);
                        }
                    }
                    // if I am on the most left side of a list and I did not visit my right
                    if (j == 0 && copy.get(i).get(j+1).equals("0")){
                        path.add(stations.get(i).get(j));
                        locations.add(stationLocations.get(i).get(j));
                        //go to right
                        findThePath(answerLocations, answerNames, copy, stations, stations.get(i).get(j+1), to, path, stationLocations, locations);
                        //Remove my location from path, because if I come here, it means the path I used is not the one that I am looking for
                        path.remove(path.size()-1);
                        locations.remove(locations.size()-1);
                    }
                    // if I am on the most right side of a list and I did not visit my left
                    if (j == stations.get(i).size()-1 && copy.get(i).get(j-1).equals("0")){
                        path.add(stations.get(i).get(j));
                        locations.add(stationLocations.get(i).get(j));
                        //go to left
                        findThePath(answerLocations, answerNames, copy, stations, stations.get(i).get(j-1), to, path, stationLocations, locations);
                        //Remove my location from path, because if I come here, it means the path I used is not the one that I am looking for
                        path.remove(path.size()-1);
                        locations.remove(locations.size()-1);
                    }
                }
            }
        }
    }
}