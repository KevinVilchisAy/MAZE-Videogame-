import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MAZEVIDEOGAME{
    static char[][] maze;//global array named maze
    static int pRow;//global player row position int
    static int pColumn;//global player column position int
    static String userKey;//global keyboard key input String 
    static boolean isActive;//global boolean that tells if the game will keep running 

    public static void main(String[] args) {
        isActive = true;//mantains the game active 
        printInitialText();//Calls the method that will print the initial text
        Scanner input = new Scanner(System.in); // scans the user input
        int levelNumber = input.nextInt();
        String fileName = mazeDetector(levelNumber);//will create a file that will be equal to the maze selected by the user in the mazeDetector method 
        maze = createSquareMaze(fileName);//Will use the method createSquareMaze to determine the size of the maze and will make it equal to the global maze array
        loadMaze(fileName);//Will use the method loadMaze to determine the elements in the maze 
        findStartPosition();//will use the method findStartPosition to start the initial position of the player
        while (isActive){//a loop that will keep the game running while the game is active 
            displayMaze(); // Print the maze, will also print the updates
            pressedKey();//Determines the player keys
            if (movePlayer()) { // Check if the move is valid
                determineIfPlayerEscaped();//Will determine if the player has escaped 
            }//if
        }//while
    }//Main Method

    public static void printInitialText(){//Will print the initial text
        System.out.println("You have been trapped by your enemy Mr. Magnificent, you need to get out of the maze.!!!");
        System.out.println("You need to use your keypressing skills to navigate and find your way out. Make sure you avoid the walls!!!(The #)");
        System.out.println("Your position is determined by the letter P, you need to reach the end of the maze marked by a F");
        System.out.println("Please select one of the three levels(mazes) in which Dr. Maleficent Squarebottom can lock you in!");
        System.out.println("1--Level One" + "\n" + "2--Level Two" + "\n" + "3--Level Three");
    }//method printInitialText

    public static String mazeDetector(int levelNumber){//Will check using the user input to know which maze is going to be used
        String fileName = "";
        switch (levelNumber) {
            case 1:
                fileName = "maze1.txt";//maze 1
                break;
            case 2:
                fileName = "maze2.txt";//maze 2
                break;
            case 3:
                fileName = "maze3.txt";//maze 3
                break;
            default:
                System.out.println("Your input is wrong, try again.");
        }//switch
        return fileName;//return the maze file that will be used 
    }//method mazeDetector

    public static char[][] createSquareMaze(String fileName){//Will determine the size of the array
        try {
            File gameLevel = new File(fileName);
            Scanner levelReader = new Scanner(gameLevel);
            int mazeSize = 0;
            while (levelReader.hasNextLine()){
                mazeSize++;
                levelReader.nextLine();
            }//while
            maze = new char[mazeSize][mazeSize];
        }catch (FileNotFoundException e){//Will output a text in case of an error
            System.out.println("There was an error while reading the file.");
        }//catch
        return maze;//will return the new size of the maze
    }//method createSquareMaze
    public static void loadMaze(String fileName){//The method will read the maze file and will determine the items of the array
        try {
            File gameLevel = new File(fileName);//the maze file to be use 
            Scanner levelReader = new Scanner(gameLevel);//scanner to read the file
            int row = 0;
            while (levelReader.hasNextLine()){//will read the file to determine the elements
                String mazeRow = levelReader.nextLine();
                for (int column = 0; column < maze[row].length; column++){
                    maze[row][column] = mazeRow.charAt(column);//will add the elements of the column
                }//for
                row++;//will change the row
            }//while
        }catch (FileNotFoundException e){
            System.out.println("There was an error while reading the file.");
        }//catch
    }//method loadMaze 
    public static void findStartPosition(){//The method will determine where the starting position is and it will assign it to the player row and column positions 
        for (int x = 0; x < maze.length; x++){//traversing the row
            for (int y = 0; y < maze[x].length; y++){//traversing the column
                if (maze[x][y] == 'S'){
                    pRow = x;//the initial player row position
                    pColumn = y;//the initial player column position
                    return;
                }//if
            }//for
        }//for
    }//method findStartPosition
    public static void displayMaze(){//the method will traverse the maze(array) and print it
        for (int x = 0; x < maze.length; x++){//row
            for (int y = 0; y < maze[x].length; y++){//column
                if (x == pRow && y == pColumn){//prints P where the player row and column location is 
                    System.out.print('P');
                }else{
                    System.out.print(maze[x][y]);
                }//else
            }//for
            System.out.println();
        }//for
    }//method displayMaze
    public static void pressedKey(){//This method records which Key is inputed, and will also check if the user would like to exit the game
        System.out.println("Please press a key to move, W-Up, S-Down, A-Left, D-Right. IF you are a loser and would like to give up and probably die just enter EXIT");
        Scanner input = new Scanner(System.in);
        userKey = input.nextLine();
    }//method pressedKey
    public static boolean movePlayer(){//This method will move the player 
        int nRow = pRow;//Considers the player row position as the new row position for the array 
        int nColumn = pColumn;//Considers the player column position as the new column position for the array 
        switch(userKey){//Will check the user inputs from the pressedKey method
            case "W":
                nRow--;//moves the array row upwards by substracting 1 from the array row 
                break;
            case "S":
                nRow++;//moves the array row downwards by adding 1 to the array row 
                break;
            case"A":
                nColumn--;//moves the array to the left by substracting 1 from the array column
                break;
            case"D":
                nColumn++;//moves the array to the left by adding 1 from the array column
                break;
            case"EXIT"://Checks if the user wants to exit the game
                System.out.println("You never left the maze... I don't know if you should be happy about that tbh");  
                isActive=false;
                break;  
            default://Check if the user inputed wrong the keys or Exit
                System.out.println("That was an invalid input, try again");
                return false;    
        }//switch movePlayer
        if(nRow<0||nRow>=maze.length||nColumn<0||nColumn>=maze.length||maze[nRow][nColumn]=='#'){//the if statements checks if the user wants to go through the wall, and it won't change the position of P
            System.out.println("STOP, have you ever seen someone go through Walls? Me neither, don't do it:/");
            return false; //will return that the player won't be able to advance 
        }//if
        pRow=nRow;//if the player doesn't want to go through a wall the positions of the player will be properly changed 
        pColumn=nColumn;
        return true;//will return that the player will be able to advance 
    }//method movePlayer
    public static void determineIfPlayerEscaped(){//This method will traverse the maze it will check where the finish line is at and will check if the player has reached that line
        int endRow = 0; //the Row in which the finish line is at
        int endColumn = 0;//the Column in which the finish line is at 
        for(int x=0;x<maze.length;x++){//traverses the rows
            for (int y=0;y<maze.length;y++){//traverses the columns
                if(maze[x][y] == 'F' && pRow == x && pColumn == y){//checks the conditions for the player to win 
                    System.out.println("CONGRATULATIONS YOU ESCAPED!!!!:PPP (Try to hide from Dr. Maleficent Squarebottom a little better next time;) ) ");
                    isActive=false;//ends the game loop
                }//IF    
            }//for
        }//for
    }//method determineIfPlayerWon
}//class