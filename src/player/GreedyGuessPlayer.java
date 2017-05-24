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

        Answer answer = new Answer();

        for (World.ShipLocation l : this.world.shipLocations) {
            for (World.Coordinate c : l.coordinates) {
                if (c.row == guess.row && c.column == guess.column) {
                    answer.isHit = true;
                    l.coordinates.remove(c);
                    if (l.coordinates.size() == 0) {
                        answer.shipSunk = l.ship;
                    }
                    break;
                }
            }
            if (answer.isHit) {
                if (answer.shipSunk != null) {
                    this.world.shipLocations.remove(l);
                    System.out.println("Ship Sunk");
                }
                break;
            }

        }

        return answer;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {

        Guess guess;
        //Base Guess on player state (Targeting Or Hunting)
        if (this.state == PlayerState.TARGETING) {
            guess = targetingGuess();
        }
        else {
            guess = huntingGuess();
        }

        // If it is not a valid guess return null.
        if (!world.updateShot(guess)) {
            return null;
        }

        return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
        System.out.println("Greedy Update");

        if (answer.isHit) {
            this.state = PlayerState.HUNTING;
        }

    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.

        // dummy return
        return true;
    } // end of noRemainingShips()

    //Process for creating a guess when in targeting mode.
    //Generates a random guess that is able to ignore every second square.
    private Guess targetingGuess() {

        //Generate a random guess to check.
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

    //Process for creating a guess when in hunting mode.
    private Guess huntingGuess() {
        System.out.println("Hunting Guess");
        return null;
    }

} // end of class GreedyGuessPlayer
