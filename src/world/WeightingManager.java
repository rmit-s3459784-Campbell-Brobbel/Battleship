package world;

import player.Guess;
import ship.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by campbellbrobbel on 25/5/17.
 */

//This class is used to manage the weightings of each cell in a board for a particular ship type.

public class WeightingManager {

    private String shipType;
    private int boardSize;
    private ArrayList<Weighting> weightings = new ArrayList<Weighting>();
    private ArrayList<Integer> weightingValues = new ArrayList<Integer>();

    private ArrayList<Ship> ships = new ArrayList<Ship>(){{
        add(new AircraftCarrier());
        add(new Battleship());
        add(new Cruiser());
        add(new Submarine());
        add(new Destroyer());
    }};

    public WeightingManager(String shipType, int boardSize) {
        this.shipType = shipType;
        this.boardSize = boardSize;
        this.generateWeightingList();

    }

    public void changeShipType(String shipType) {
        this.weightings.clear();
        this.shipType = shipType;
        this.weightingValues.clear();
        this.generateWeightingList();
    }

    public String getShipType() {
        return shipType;
    }

    public Guess highestWeightedGuess() {

        boolean loop = true;

        for (Weighting w : this.weightings) {
            if (w.getWeighting() == this.weightingValues.get(this.weightingValues.size()-1)) {

                return w.getGuess();
            }
        }

        Guess guess = null;
        this.weightingValues.remove(this.weightingValues.size()-1);
        guess = this.highestWeightedGuess();

        return guess;
    }

    public void removeGuess(Guess guess) {
        for (Weighting w: this.weightings) {
            if (w.getGuess().equals(guess)) {
                this.weightings.remove(w);
                break;

            }
        }
    }

    //Generate a weighting for every cell in a given battleship board.
    private void generateWeightingList() {
        System.out.println("Board Size: " + this.boardSize);
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                Guess g = new Guess();
                g.row = x;
                g.column = y;
                Ship tempShip = null;
                for (Ship ship : ships) {
                    if (this.shipType.equals(ship.name())) {
                        tempShip = ship;
                    }
                }
                Weighting w = new Weighting(tempShip.name(), g, this.boardSize, tempShip.len());
                if (!this.weightingValues.contains(w.getWeighting())) {
                    this.weightingValues.add(w.getWeighting());
                }
                this.weightings.add(w);
            }
        }
        Collections.sort(this.weightingValues);

    }

    public void printAllWeightings() {

        System.out.println("Weightings For: " + this.shipType);
        System.out.println("------------------------------------");
        for (Weighting w : this.weightings){
            System.out.println(w.getGuess().toString());
        }

    }

    //The amount of potential different weighting values is the size of the ship * 2

}
