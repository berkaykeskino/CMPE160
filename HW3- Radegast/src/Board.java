/*
Muhammet Berkay Keskin 2021400249 09.05.2023
First the map is extracted from the txt file and stored as an object in Board class
Then user gives input to make modifications on the board object
Next a copy of the modified board is kept in an array to compare at the end
After that the lakes and the height of the lakes are found
Finally the first and the last arrays are compared to calculate the score
 */

import java.util.ArrayList;

public class Board {
    //width is the number of columns, height is number of rows
    private final int width;
    private final int height;
    //used to store the volume of each lake
    private int sum = 0;
    //counts the number of lakes
    private int counter = 0;
    //array to store towers
    public Tower[][] tower;
    //used to determine the difference between the first and the last board and calculate score
    public int[][] copyOfOriginal;
    //if true, it is a lake
    private boolean[][] isLake;
    //the x-axis at the bottom, holds the letters a b c ...
    private final String[] locX;
    //used to sign the places that are visited in the recursion functions
    private boolean[][] visited;
    //used to store heights of the lakes
    private ArrayList<Integer> maxHeightOfLakes = new ArrayList<Integer>();
    //to store the score
    private double score = 0;
    //the final board with signed lakes
    private String[][] answer;
    //alphabet is used to specify locx
    private String alphabet;
    //to visit the neighbours of a tower in the board
    private int[][] neighbours;
    //used in recursive functions to store all towers' coordinates in a lake
    ArrayList<Integer[]> lakes = new ArrayList<Integer[]>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.tower = new Tower[height][width];
        this.isLake = new boolean[height][width];
        this.answer = new String[height][width];
        this.visited = new boolean[height][width];
        this.copyOfOriginal = new int[height][width];
        this.locX = new String[width];
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";

        this.neighbours = new int[8][2];
        neighbours[0] = new int[]{-1, -1};
        neighbours[1] = new int[]{-1, 0};
        neighbours[2] = new int[]{-1, 1};
        neighbours[3] = new int[]{0, -1};
        neighbours[4] = new int[]{0, 1};
        neighbours[5] = new int[]{1, -1};
        neighbours[6] = new int[]{1, 0};
        neighbours[7] = new int[]{1, 1};

        //used to name the columns
        int counter = 0;
        //if the columns have one lettered names
        for (int i = 0; i < 26; i++) {
            locX[counter] = String.valueOf(alphabet.charAt(i));
            counter++;
            if (counter == width) {
                break;
            }
        }
        //if the columns have two lettered names
        if (counter != width) {
            //first letter
            for (int i = 0; i < 27; i++) {
                boolean go = true;
                //second letter
                for (int j = 0; j < 26; j++) {
                    locX[counter] = String.valueOf(alphabet.charAt(i)) + String.valueOf(alphabet.charAt(j));
                    counter++;
                    if (counter == width) {
                        go = false;
                        break;
                    }
                }
                if (!go) {
                    break;
                }
            }
        }
    }

    //getter for height, used in main method to take rows
    public int getHeight() {
        return height;
    }

    //a row in the txt file is given with index of it
    public void addRow(String row, int indexOfRow) {
        //myList stores the numbers in the row
        String[] myList = row.split(" ");
        for (int i = 0; i < width; i ++) {
            //a new tower is created in the board for each tower in the row
            this.tower[indexOfRow][i] = new Tower(Integer.valueOf(myList[i]));
        }
    }

    //to see the board in main
    public void showBoard(boolean mod) {
        int counter = 0;
        //for all rows
        for (Tower[] i : this.tower) {
            //write the index of the row
            if (counter < 10) {
                System.out.print("  " + counter + " ");
            } else {
                System.out.print(" " + counter + " ");
            }
            //for all towers in the row
            for (Tower j : i) {
                //write the height of the tower
                if (j.getHeight() > 9){
                    System.out.print(j.getHeight() + " ");
                    continue;
                }
                //if mod is true, there is no space at the end of the line
                if (counter == 0 && j.equals(tower[0][tower[0].length-1]) && mod){
                    System.out.print(" " + j.getHeight());
                    break;
                }
                System.out.print(" " + j.getHeight() + " ");
            }
            System.out.println();
            counter++;
        }
        System.out.print("   ");
        //locX stored the column names
        for (String i : locX) {
            //write column names
            if (i.length() == 1) {
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.println(" ");
    }



    public boolean determineLakes(int[] konumum) {
        //add my location as i am a lake
        lakes.add(new Integer[]{konumum[0], konumum[1]});
        //if i am a neighbour of the outermost square
        if (konumum[0] == 1 || konumum[0] == tower.length - 2 || konumum[1] == 1 || konumum[1] == tower[0].length - 2) {
            for (int[] i : neighbours) {
                //if one of my neighbours who is in the outermost square
                if (konumum[0] + i[0] == 0 || konumum[0] + i[0] == tower.length - 1 || konumum[1] + i[1] == 0 || konumum[1] + i[1] == tower[0].length - 1) {
                    //is lower than or equal to me
                    if (tower[konumum[0] + i[0]][konumum[1] + i[1]].getHeight() <= tower[konumum[0]][konumum[1]].getHeight()) {
                        //i am not a lake
                        return false;
                    }
                }
            }
        }
        for (int[] i : neighbours) {
            //if i visited this neighbour
            if (visited[konumum[0] + i[0]][konumum[1] + i[1]]) {
                //don't visit again
                continue;
            }
            //if my neighbour has the same height as me
            if (tower[konumum[0] + i[0]][konumum[1] + i[1]].getHeight() == tower[konumum[0]][konumum[1]].getHeight()) {
                //visit my neighbour
                visited[konumum[0]][konumum[1]] = true;
                return determineLakes(new int[]{konumum[0] + i[0], konumum[1] + i[1]});
            }
        }
        //increment the value of my location and return true
        //if i dont have neighbours to visit anymore
        tower[konumum[0]][konumum[1]].increment();
        return true;
    }
    public void determineLakes(){
        //max is the maximum height in the board
        int max = 0;
        for (Tower[] a : tower){
            for (Tower b : a){
                if (b.getHeight() > max){
                    max = b.getHeight();
                }
            }
        }
        isLake = new boolean[height][width];
        int counter = 0;
        //for all numbers smaller than max+1 in the board
        while (counter!= max+1){
            //for all towers inside the outermost square
            for (int i = 1; i < tower.length - 1 ; i++){
                for (int j = 1; j < tower[0].length - 1; j++){
                    //if the tower's height is equal to counter - 1
                    if (tower[i][j].getHeight() == counter - 1){
                        //call recursive determineLakes,if true lakes has lakes in it
                        if (determineLakes(new int[] {i,j})) {
                            for (Integer[] a : lakes) {
                                //the last lake's value was incremented in the recursive function
                                if (!(a.equals(lakes.get(lakes.size()-1)))){
                                    tower[a[0]][a[1]].increment();
                                }
                                isLake[a[0]][a[1]] = true;
                            }
                        }

                    }
                    //redefine the variable for the new recursion
                    lakes = new ArrayList<>();
                    visited = new boolean[height][width];
                }
            }
            counter++;

        }

    }





    private void determineHeight(int[] konumum, int maxValue){
        //mark here as visited
        visited[konumum[0]][konumum[1]] = true;
        //for all my neighbours
        for (int[] i : neighbours){
            //if my neighbours is not a lake and its height is lower than maxValue
            if (!isLake[konumum[0] + i[0]][konumum[1] + i[1]] && maxValue > tower[konumum[0]+i[0]][konumum[1]+i[1]].getHeight()){
                //max value of the lake is equal to that neighbour's height
                maxValue = tower[konumum[0]+i[0]][konumum[1]+i[1]].getHeight();
            }
        }
        boolean bu = true;
        for (int[] i : neighbours){
            //if i am a lake, for my lake neighbours, call recursive function
            if (isLake[konumum[0] + i[0]][konumum[1] + i[1]] && isLake[konumum[0]][konumum[1]] && !visited[konumum[0] + i[0]][konumum[1] + i[1]] ){
                bu = false;
                determineHeight(new int[] {konumum[0]+i[0],konumum[1]+i[1]}, maxValue);
            }
        }
        //if i do not have lake neighbours and i am a lake, my maxValue is my lowest neighbour
        if (bu && isLake[konumum[0]][konumum[1]]){
            maxHeightOfLakes.add(maxValue);
        }

    }
    public void determineHeight(){
        //height of lakes has to be lower than the maxHeight
        int maxHeight = 0;
        for (Tower[] i : tower){
            for (Tower j : i){
                if (j.getHeight() > maxHeight){
                    maxHeight = j.getHeight();
                }
            }
        }
        for (int i = 1; i < tower.length - 1 ; i++){
            for (int j = 1; j < tower[0].length - 1 ; j++){
                //if i didn't visit this square, call recursive function
                if (!visited[i][j]){
                    determineHeight(new int[] {i,j}, maxHeight);
                }
            }
        }
        visited = new boolean[height][width];
    }




    private void putLetters(int[] konumum){
        visited[konumum[0]][konumum[1]] = true;
        boolean noNeighbours = true;
        for (int[] i : neighbours){
            //if i am a lake, my neighbour is a lake and wasn't visited
            if (isLake[konumum[0] + i[0]][konumum[1] + i[1]] && isLake[konumum[0]][konumum[1]] && !visited[konumum[0] + i[0]][konumum[1] + i[1]] ){
                noNeighbours = false;
                //the amount of money in the lake is added to sum
                sum +=(maxHeightOfLakes.get(counter) - copyOfOriginal[konumum[0]][konumum[1]]);
                //the tower is assigned a letter
                answer[konumum[0]][konumum[1]] = returnLetter(counter);
                putLetters(new int[] {konumum[0]+i[0],konumum[1]+i[1]});
            }
        }
        //if i am a lake and i don't have neighbours to visit more
        if (noNeighbours && isLake[konumum[0]][konumum[1]]){
            sum +=(maxHeightOfLakes.get(counter) - copyOfOriginal[konumum[0]][konumum[1]]);
            answer[konumum[0]][konumum[1]] = returnLetter(counter);
            calculateScore(sum);
            sum = 0;
            counter++;

        }

    }
    public void putLetters(){
        for (int i = 1; i < tower.length - 1 ; i++){
            for (int j = 1; j < tower[0].length - 1 ; j++){
                if (!visited[i][j]){
                    //recursive function is called for all towers in the outermost square
                    putLetters(new int[] {i,j});
                }
            }
        }

    }

    public String returnLetter(int counter){
        int sayac = 0;
        int index = 0;
        int yedek = 0;
        if (counter<alphabet.length()){
            //if number of lakes is smaller than alphabet's length
            return String.valueOf(alphabet.charAt(counter));
        }else {
            //redefine counter and produce the 2 lettered name
            counter -= alphabet.length();
            while (sayac != counter){
                sayac++;
                yedek++;
                if (yedek == alphabet.length()){
                    yedek = 0;
                    index++;
                }
            }
        }
        return String.valueOf(alphabet.charAt(index))+String.valueOf(alphabet.charAt(yedek));

    }



    private void calculateScore(int sayi){
        this.score += Math.pow(sayi,0.5);
    }

    public void showAnswer(){
        for (int i = 0; i < answer.length; i++){
            for (int j = 0; j < answer[0].length; j++){
                //if there is no letter, get the height
                if (answer[i][j] == null){
                    answer[i][j] = Integer.toString(tower[i][j].getHeight());
                }
            }
        }

        int counter = 0;
        //show answer with the same algorithm used in showBoard function
        for (String[] i : this.answer){
            if (counter < 10){
                System.out.print("  "+counter+" ");
            }else {
                System.out.print(" "+counter+" ");
            }

            for (String j : i){
                if (j.length()==1){
                    System.out.print(" "+j.toUpperCase()+" ");
                }else{
                    System.out.print(j.toUpperCase()+" ");
                }

            }
            System.out.println();
            counter++;
        }
        System.out.print("   ");
        for (String i : locX){
            if (i.length()==1){
                System.out.print("  "+i);
            }else {
                System.out.print(" "+i);
            }
        }
        System.out.println(" ");
        //format the score
        System.out.print("Final score: "+ String.format("%.2f", score).replace(",","."));
    }
    public boolean inputFormat(String loc){
        String numbers = "0123456789";
        for (int i = 0 ; i < loc.length(); i++){
            //if the input contains some character not in both alphabet and numbers, wrong input
            if (!(alphabet.contains(loc.substring(i,i+1))) && !(numbers.contains(loc.substring(i,i+1)))){
                return false;
            }
        }
        String locY = "";
        String locX = "";
        boolean sawNumber = false;
        for (int i = 0 ; i < loc.length(); i++){
            //if it is a letter
            if (!(numbers.contains(loc.substring(i,i+1)))){
                locX += loc.charAt(i);
                if (sawNumber){
                    return false;
                }
            }else {//if i see a number, mark sawNumber as true, if i see a character after that, i will return false
                locY += loc.charAt(i);
                sawNumber = true;
            }
        }
        //if no numbers, return false
        if (!sawNumber){
            return false;
        }
        //if input's height is greater than the board's height, return false
        int locY2 = Integer.parseInt(locY);
        if (locY2 >= height){
            return false;
        }
        //if the letters are defined in the x-axis, return true
        for (String i : this.locX){
            if (locX.equals(i)){
                return true;
            }
        }
        return false;
    }


    public void increment(String loc){
        String locY = "";
        String locX = "";
        //locX holds the letters and locY holds the numbers
        for (int i = 0 ; i < loc.length(); i++){
            if (alphabet.contains(loc.substring(i,i+1))){
                locX += loc.charAt(i);
            }else{
                locY += loc.charAt(i);
            }
        }
        int posX = 0;
        int posY = Integer.parseInt(locY);
        //determine the index of the letters
        for (int i = 0; i < this.locX.length ; i++){
            if (this.locX[i].equals(locX)){
                posX = i;
                break;
            }
        }
        //increment the coordinate's height
        this.tower[posY][posX].increment();
    }
    public void createCopy(){
        //store the original board
        for (int i = 0; i<tower.length;i++){
            for (int j = 0; j < tower[0].length;j++ ){
                copyOfOriginal[i][j] = tower[i][j].getHeight();
            }
        }
    }
}
