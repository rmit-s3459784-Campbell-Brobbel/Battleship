package world;

import player.Guess;

import java.util.ArrayList;

/**
 * Created by campbellbrobbel on 25/5/17.
 */
public class Weighting {

    private String shipType= "";
    private int shipSize;
    private int weighting;
    private Guess guess;

    public Weighting(String shipType, Guess guess, int boardSize, int shipSize) {
        this.shipType = shipType;
        this.guess = guess;
        this.shipSize = shipSize;
        this.weighting = this.calculateWeighting(boardSize);
    }

    public int getWeighting() {
        return weighting;
    }

    public Guess getGuess() {
        return guess;
    }

    //Used to calculate the weighting of a particular cell
    //based on board size, ship type and cell location
    private int calculateWeighting(int boardSize) {

        int vConfigs = 0;
        int hConfigs = 0;

        int upSquares = guess.column + (this.shipSize-1) > boardSize ? boardSize - guess.column : shipSize - 1;
        int downSquares = guess.column - (this.shipSize-1) < 0 ? guess.column : shipSize -1;
        int leftSquares = guess.row + (this.shipSize-1) > boardSize ? boardSize - guess.row : shipSize - 1;
        int rightSquares = guess.row - (this.shipSize-1) < 0 ? guess.row : shipSize - 1;

        vConfigs = (upSquares + downSquares + 1) - (shipSize - 1);
        hConfigs = (leftSquares + rightSquares + 1) - (shipSize - 1);

        return vConfigs + hConfigs;
    }

}
