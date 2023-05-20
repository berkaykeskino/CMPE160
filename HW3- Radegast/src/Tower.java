/*
Muhammet Berkay Keskin 2021400249 09.05.2023
First the map is extracted from the txt file and stored as an object in Board class
Then user gives input to make modifications on the board object
Next a copy of the modified board is kept in an array to compare at the end
After that the lakes and the height of the lakes are found
Finally the first and the last arrays are compared to calculate the score
 */



public class Tower {
    //each tower has its height
    private int height;

    //specify the height of the tower
    public Tower(int height){
        this.height = height;
    }

    //will be used in the board class to compare neighbours and calculate the score
    public int getHeight(){
        return this.height;
    }

    //make modifications and use when filling the lakes
    public void increment(){
        this.height++;
    }
}
