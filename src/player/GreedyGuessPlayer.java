package player;

import java.util.ArrayList;
import java.util.Random;

import world.World;

/**
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class GreedyGuessPlayer  implements Player{

    public enum PlayerState {
        TARGETING, HUNTING
    }
    public enum HuntingDirection {
        NORTH, SOUTH, EAST, WEST
    }

    PlayerState state = PlayerState.HUNTING;
    World world;

    ArrayList<HuntingDirection> remainingHDirections = new ArrayList<HuntingDirection>();
    HuntingDirection currentHDir;
    ArrayList<Guess> hits = new ArrayList<Guess>();

    @Override
    public void initialisePlayer(World world) {
        this.world = world;
        resetRemainingTargetingDirections();


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
                        System.out.println("Sunk This: " + l.ship.name());
                        answer.shipSunk = l.ship;
                    }
                    break;
                }
            }
            if (answer.isHit) {
                if (answer.shipSunk != null) {
                    this.world.shipLocations.remove(l);
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
        if (this.state == PlayerState.HUNTING) {
            guess = huntingModeGuess();
        }
        else {
            guess = targetingModeGuess();

            while (guess == null) {
             //Move to next direction
                changeTargetingDirection();
                guess = targetingModeGuess();
            }
        }

        return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
                if (this.state == PlayerState.HUNTING) {
            if (answer.isHit) {
                this.state = PlayerState.TARGETING;
                this.hits.add(guess);
            }
        }
        //If state is Targeting
        else {
            if (answer.isHit) {
                this.hits.add(guess);

                if (answer.shipSunk != null) {
                    //Ship is sunk. Clear out the hits and reset remaining directions.
                    this.hits.clear();
                    this.resetRemainingTargetingDirections();
                    this.state = PlayerState.HUNTING;
                }
            }
            else {
                changeTargetingDirection();
            }
        }
    } // end of update()


    @Override
    public boolean noRemainingShips() {
        if (world.shipLocations.size()==0){
            return true;
        }
        return false;
    } // end of noRemainingShips()

    //Process for creating a guess when in hunting mode.
    //Generates a random guess that is able to ignore every second square.
    private Guess huntingModeGuess() {

        //Generate a random guess to check.
        Random random = new Random();
        int row = random.nextInt(10);
        int column;

        // If the random row is even
        if (row % 2 == 0) {
            do {
                column = random.nextInt(10);
            }while (column % 2 != 0);
        }
        //If the random row is odd
        else {
            do {
                column = random.nextInt(10);
            }while (column % 2 == 0);
        }

        Guess guess = new Guess();
        guess.column = column;
        guess.row = row;
        return guess;
    }

    //Process for creating a guess when in targeting mode.
    private Guess targetingModeGuess() {

        int offset = hits.size();
        Guess firstGuess = hits.get(0);
        Guess nextGuess = new Guess();

        switch (this.currentHDir) {
            case NORTH:
                if (firstGuess.row + offset > 10){
                    return null;
                }
                nextGuess.row = firstGuess.row + offset;
                nextGuess.column = firstGuess.column;
                break;
            case SOUTH:
                if (firstGuess.row - offset < 0){
                    return null;
                }
                nextGuess.row = firstGuess.row - offset;
                nextGuess.column = firstGuess.column;
                break;
            case WEST:
                if (firstGuess.column + offset > 10){
                    return null;
                }
                nextGuess.column = firstGuess.column + offset;
                nextGuess.row = firstGuess.row;

                break;
            case EAST:
                if (firstGuess.column - offset < 0){
                    return null;
                }
                nextGuess.column = firstGuess.column - offset;
                nextGuess.row = firstGuess.row;
                break;
        }
        return nextGuess;
    }

    private void resetRemainingTargetingDirections() {
        this.remainingHDirections.clear();
        this.remainingHDirections.add(HuntingDirection.NORTH);
        this.remainingHDirections.add(HuntingDirection.SOUTH);
        this.remainingHDirections.add(HuntingDirection.EAST);
        this.remainingHDirections.add(HuntingDirection.WEST);
        this.currentHDir = this.remainingHDirections.get(0);

    }

    private void changeTargetingDirection() {
            this.remainingHDirections.remove(0);
            this.currentHDir = this.remainingHDirections.get(0);
            ArrayList<Guess> tempList = new ArrayList<Guess>();
            tempList.add(hits.get(0));
            hits.clear();
            hits.add(tempList.get(0));
    }

} // end of class GreedyGuessPlayer
