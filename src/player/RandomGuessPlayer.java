package player;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import world.*;

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
        for (World.ShipLocation l : world.shipLocations) {
            System.out.println(l);
        }
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.

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

        // dummy return
        return answer;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
        // To be implemented.

        Guess guess = new Guess();
        int row;
        int column;

        Random random = new Random();

        guess.row = random.nextInt(10);
        guess.column = random.nextInt(10);

        world.updateShot(guess);

        // dummy return
        return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
       //Method is redundant in RandomGuessPlayer

    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.
        if (world.shipLocations.size()==0){
            return true;
        }
        // dummy return
        return false;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
