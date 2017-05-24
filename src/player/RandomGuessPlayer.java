package player;

import java.util.Scanner;
import world.World;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class RandomGuessPlayer implements Player{

    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        System.out.println("IN the player INIT");
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.
        System.out.println("IN the player INIT");

        // dummy return
        return null;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
        // To be implemented.
        System.out.println("IN the player INIT");

        // dummy return
        return null;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
        System.out.println("IN the player INIT");

    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.
        System.out.println("IN the player INIT");

        // dummy return
        return true;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
