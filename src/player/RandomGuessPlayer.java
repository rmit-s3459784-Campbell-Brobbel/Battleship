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

    World world;

    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        this.world = world;
        System.out.println("Init Player");
        for (World.ShipLocation l : world.shipLocations) {
            System.out.println(l);
        }
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.
        System.out.println("Get Answer");

        // dummy return
        return null;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
        // To be implemented.
        System.out.println("Make Guess");
        // dummy return
        return null;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
        System.out.println("update");

    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.
        System.out.println("Remaining Ships");

        // dummy return
        return true;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
