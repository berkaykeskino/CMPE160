/*
Muhammet Berkay Keskin 2021400249 09.05.2023
First the map is extracted from the txt file and stored as an object in Board class
Then user gives input to make modifications on the board object
Next a copy of the modified board is kept in an array to compare at the end
After that the lakes and the height of the lakes are found
Finally the first and the last arrays are compared to calculate the score
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class MuhammetBerkay_Keskin {
    public static void main(String[] args) throws FileNotFoundException {
        //change the language
        Locale.setDefault(Locale.US);
        //txt file to extract the board
        String fileName = "input.txt";
        File file = new File(fileName);
        //If it doesn't exist, give exception
        if (!file.exists()) {
            System.out.printf("%s can not be found.", fileName);
            System.exit(1); // exit the program
        }
        Scanner inputFile = new Scanner(file);
        //The first line is the width and height
        String widthAndHeight = inputFile.nextLine();
        String width = "";
        String height = "";
        //width before space, height after space
        boolean space = false;
        for (int i = 0; i < widthAndHeight.length(); i++){
            //if i see space
            if (widthAndHeight.charAt(i) == ' '){
                space = true;
                continue;
            }
            //i didn't see the space, this is width
            if (!space){
                width = width + widthAndHeight.charAt(i);
            }
            else {//i saw the space, this is height
                height = height + widthAndHeight.charAt(i);
            }
        }
        //create a board object
        Board board = new Board(Integer.parseInt(width), Integer.parseInt(height));

        //for the numbers in the txt file
        for (int i = 0; i < board.getHeight() ; i++){
            //take the first row
            String addThis = inputFile.nextLine();
            //add row to the board object's tower array
            board.addRow(addThis, i);
        }
        //show the board at the beginning
        board.showBoard(true);

        //to take modifications
        Scanner scanner = new Scanner(System.in);
        //10 modifications
        for (int i = 0; i < 10; i++){
            System.out.println("Add stone "+(i+1)+" / 10 to coordinate:");
            String change = scanner.nextLine();
            //while change is not in the correct format, change is a new scanner.nextline
            while (!(board.inputFormat(change))){
                System.out.println("Wrong input!");
                change = scanner.nextLine();
            }

            //increment function takes info in letterNumber format and increments that coordinate
            board.increment(change);
            //show the board after each modifications
            board.showBoard(false);
            System.out.println("---------------");
        }

        //create a copy of the modified array
        board.createCopy();
        //determine the lakes
        board.determineLakes();
        //determine maksimum height of the lakes
        board.determineHeight();
        //calculate the score and label the lakes
        board.putLetters();
        //show final result
        board.showAnswer();

    }
}