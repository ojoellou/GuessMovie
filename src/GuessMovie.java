import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

//the beginning
public class GuessMovie {
    char[] lettersFound;
    int numTrials =10;
    String wordToFind;
    int numErrors;
    ArrayList <String> letters = new ArrayList <>();

    //method returning random movie
    public String nextWordToFind() throws Exception{
        //To read movies from txt file
        File movieFile = new File("movies.txt");
        Scanner movieScanner = new Scanner(movieFile);
        //creating an arraylist
        ArrayList<String> movies = new ArrayList<>();
        //To read movies line by line
        while (movieScanner.hasNextLine()){
            String movieLine = movieScanner.nextLine();
            movies.add(movieLine);
        }
        //To get a random movie from arraylist
        int randomNumber = (int) (Math.random()*movies.size() +1);
        String randomMovie = movies.get(randomNumber);
        return randomMovie;
    }

    //method for starting a new game
    public void newGame() throws Exception{
        //setting to default
        numErrors=0;
        letters.clear();
        wordToFind = nextWordToFind();

        //introducing game
        Scanner scanPlayerName = new Scanner(System.in);
        System.out.println("Guess a movie or Die! Enter player's name to continue_");
        String playerName = scanPlayerName.nextLine();
        //Game instructions
        System.out.println("Welcome " + playerName + ". I have chosen a movie title at random. Guess what movie it is");
        System.out.println("You have 10 points to guess right. Guess incorrectly and you lose a live.");

        //initializing lettersFound
        lettersFound = new char[wordToFind.length()];
        for (int i = 0; i < lettersFound.length; i++) {
            lettersFound[i] = '_';
        }
    }

    //method returning true if word is found by user
    public boolean isMovieFound(){
        return wordToFind.contentEquals(new String(lettersFound));
    }

    //method to update lettersFound after user enters a character
    private void input (String newLetter) {
        //update if newLetter has not already been entered
        if (!letters.contains(newLetter)) {
            //check if wordToFind contains newLetter
            if (wordToFind.contains(newLetter)) {
                //replace _ with newLetter
                int index = wordToFind.indexOf(newLetter);

                while (index >= 0) {
                    //to pick only the first letter from player's entry
                    lettersFound[index] = newLetter.charAt(0);
                    //to check if newLetter has another occurence
                    index = wordToFind.indexOf(newLetter, index+1);
                }
            } else {
                //newLetter is not in movie
                numErrors++;
            }
            //add newLetter to letters
            letters.add(newLetter);
        }
    }

    //method returning current state of word found
    private String lettersFoundContent() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < lettersFound.length; i++){
            builder.append(lettersFound[i]);
            //what does this do?
            if ( i< lettersFound.length-1){
                builder.append(" ");
            }
        }
        return builder.toString();
    }


    //method to play game
    public void play() throws Exception{
        //to read input from user
        Scanner guess = new Scanner(System.in);
        while (numErrors < numTrials){
            System.out.println("Enter a letter: ");
            String letterGuess = guess.nextLine();

            //keeping only first letter of guess
            if (letterGuess.length() > 1){
                letterGuess = letterGuess.substring(0,1);
            }

            //process letter typed using input method
            input(letterGuess);
            //display current state
            System.out.println("You are guessing: " +lettersFoundContent());

            //checking if word has been found
            if (isMovieFound()){
                System.out.println("You win!");
                break;
            }else {
                //display number of trials remaining
                System.out.println("You have " + (numTrials-numErrors) + " lives remaining");
                if (numErrors==numTrials && isMovieFound()==false){
                    System.out.println("The movie is " + wordToFind);
                }
            }
        }
    }

    public static void main (String[] args) throws Exception{
        GuessMovie guessMovie = new GuessMovie();
        guessMovie.newGame();
        guessMovie.play();
    }
}