package player;

import java.util.Random;
import java.util.Scanner;
import world.World;

/**
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class GreedyGuessPlayer  implements Player{

    private enum PlayerState {
        TARGETING, HUNTING
    }

    PlayerState state = PlayerState.TARGETING;
    World world;
    @Override
    public void initialisePlayer(World world) {
        this.world = world;

        System.out.println("Greedy Init");
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.
        System.out.println("Greedy Get Answer");
        // dummy return
        return null;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {

        Guess guess;
        //Base Guess on player state (Targeting Or Hunting)
        if (this.state == PlayerState.TARGETING) {

            guess = targetingGuess();
            System.out.println(guess.toString());

        }
        else {
            guess = huntingGuess();
        }

        world.updateShot(guess);

        // dummy return
        return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
        System.out.println("Greedy Update");
    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.

        // dummy return
        return true;
    } // end of noRemainingShips()

    //Process for creating a guess when in targeting mode.
    private Guess targetingGuess() {
        System.out.println("Targeting Guess");

        //Generate a random guess to check.
        Guess guess = generateRandomGuess();

        return guess;
    }

    //Process for creating a guess when in hunting mode.
    private Guess huntingGuess() {
        System.out.println("Hunting Guess");
        return null;
    }

    //Generates a random guess that is able to ignore every second square.
    private Guess generateRandomGuess() {
        Random random = new Random();
        int row = random.nextInt(9);
        int column;

        // If the random row is even
        if (row % 2 == 0) {
            do {
                column = random.nextInt(9);
            }while (column % 2 != 0);
        }
        //If the random row is odd
        else {
            do {
                column = random.nextInt(9);
            }while (column % 2 == 0);
        }

        Guess guess = new Guess();
        guess.column = column;
        guess.row = row;

        return guess;
    }
} // end of class GreedyGuessPlayer
