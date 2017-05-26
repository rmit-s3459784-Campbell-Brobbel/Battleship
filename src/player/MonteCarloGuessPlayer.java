package player;

import java.util.ArrayList;

import world.WeightingManager;
import world.World;


/**
 * Monte Carlo guess player (task C).
 * Please implement this class.
 *
 * @author Youhan, Jeffrey
 */
public class MonteCarloGuessPlayer  implements Player{

    GreedyGuessPlayer.PlayerState state = GreedyGuessPlayer.PlayerState.HUNTING;
    World world;

    ArrayList<GreedyGuessPlayer.HuntingDirection> remainingHDirections = new ArrayList<GreedyGuessPlayer.HuntingDirection>();
    GreedyGuessPlayer.HuntingDirection currentHDir;
    ArrayList<Guess> hits = new ArrayList<Guess>();
    ArrayList<String> remainingEnemyShips = new ArrayList<String>(){{
        add("AircraftCarrier");
        add("Battleship");
        add("Submarine");
        add("Cruiser");
        add("Destroyer");
    }};
    WeightingManager manager;

    @Override
    public void initialisePlayer(World world) {
        for (String s : this.remainingEnemyShips) {
            System.out.println(s);
        }
        this.world = world;
        resetRemainingTargetingDirections();
        this.manager = new WeightingManager(remainingEnemyShips.get(0), 10);

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

    private boolean removeEnemyShipFromList(String shipType) {
        if (this.remainingEnemyShips.size() == 0) {
            return false;
        }

        for (String s : this.remainingEnemyShips) {
            if (s.equals(shipType)) {
                this.remainingEnemyShips.remove(shipType);
                System.out.println("Changing Ship Types");
                if (this.remainingEnemyShips.size() > 0 && !this.manager.getShipType().equals(this.remainingEnemyShips.get(0))) {
                    this.manager.changeShipType(this.remainingEnemyShips.get(0));
                }
                return true;
            }
        }

        System.out.println("Not A Valid Ship Type");
        return false;
    }

    @Override
    public Guess makeGuess() {

        Guess guess;
        //Base Guess on player state (Targeting Or Hunting)
        if (this.state == GreedyGuessPlayer.PlayerState.HUNTING) {
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

        if (this.state == GreedyGuessPlayer.PlayerState.HUNTING) {
            if (answer.isHit) {
                this.state = GreedyGuessPlayer.PlayerState.TARGETING;
                this.hits.add(guess);

                if (answer.shipSunk != null) {
                    removeEnemyShipFromList(answer.shipSunk.name());
                }
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
                    this.state = GreedyGuessPlayer.PlayerState.HUNTING;
                    removeEnemyShipFromList(answer.shipSunk.name());

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

        Guess guess = this.manager.highestWeightedGuess();
        this.manager.removeGuess(guess);
        System.out.println("Currently Searching For: " + this.manager.getShipType());
        //System.out.println("Guess R-" + guess.row + " C-" + guess.column);
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

    //Resets remaining directions left to check.
    private void resetRemainingTargetingDirections() {
        this.remainingHDirections.clear();
        this.remainingHDirections.add(GreedyGuessPlayer.HuntingDirection.NORTH);
        this.remainingHDirections.add(GreedyGuessPlayer.HuntingDirection.SOUTH);
        this.remainingHDirections.add(GreedyGuessPlayer.HuntingDirection.EAST);
        this.remainingHDirections.add(GreedyGuessPlayer.HuntingDirection.WEST);
        this.currentHDir = this.remainingHDirections.get(0);

    }

    // When in targeting mode we pick a direction to search in.
    // If that direction hits a ship we keep moving in that direction.
    // Otherwise we go back to the cell that was hit first and go in a different direction.
    private void changeTargetingDirection() {
        this.remainingHDirections.remove(0);
        this.currentHDir = this.remainingHDirections.get(0);
        ArrayList<Guess> tempList = new ArrayList<Guess>();
        tempList.add(hits.get(0));
        hits.clear();
        hits.add(tempList.get(0));
    }
} // end of class MonteCarloGuessPlayer
