// =============================================================================
/**
 * The <code>Game</code> class.  Manage a single <i>Game of Life</i> by loading
 * the initial grid of cells and evolving them as requested.
 *
 * @author Scott F. Kaplan -- sfkaplan@cs.amherst.edu
 **/
// =============================================================================



// =============================================================================
// IMPORTS

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
// =============================================================================



// =============================================================================
public class Game {
// =============================================================================



    // =========================================================================
    // DATA MEMBERS

    /**
     * The grid itself.
     **/
    private Grid _grid;

    /**
     * The current generation number.
     **/
    private int _generation;
    // =========================================================================



    // =========================================================================
    /**
     * The constructor.  Read the initial state of a game from a provided
     * pathname, creating the <code>Grid</code> and the specified live
     * <code>Cell</code>s.
     *
     * @param initialStatePath The file from which the initial state of the
     *                         universe is taken.
     **/
    public Game (String initialStatePath) {

        // Read the initial state, creating a grid of cells as specified.
        readInitialState(initialStatePath);

        // Start counting at generation 0.
        _generation = 0;

    } // Game()
    // =========================================================================



    // =========================================================================
    /**
     * Read the initial state file, creating a <code>Grid</code> and
     * initializing it with <code>Cell</code>s as specified by that file.
     *
     * @param initialStatePath The file from which the initial state of the
     *                         universe is taken.
     **/
    private void readInitialState (String initialStatePath) {

        // Create the reader for this file.
        Scanner reader = null;
        try {
            reader = new Scanner(new File(initialStatePath));
        } catch (FileNotFoundException e) {
            Support.abort("ERROR: File not found: " + initialStatePath);
        }

        // Read the first line, which contains the dimensions of the grid.
        int rows    = -1;
        int columns = -1;
        try {
            rows    = reader.nextInt();
            columns = reader.nextInt();
        } catch (InputMismatchException e) {
            Support.abort("ERROR: Invalid dimensions at line 1");
        }

        // Create a Grid with these dimensions.
        _grid = new Grid(rows, columns);

        // Read coordinates for initially live cells until the end-of-file is
        // reached.
        int lineNumber = 2;
        while (true) {

            int row = -1;
            int col = -1;
            try {
                row = reader.nextInt();
                col = reader.nextInt();
            } catch (InputMismatchException e) {
                Support.abort("ERROR: From initial state file, " +
                        "could not read coordinates at line " +
                        lineNumber);
            } catch (NoSuchElementException e) {
                break;
            }

            // Set the cell to be alive.
            _grid.getCell(row, col).makeAlive();

            lineNumber += 1;

        }

    } // readInitialState ()
    // =========================================================================



    // =========================================================================
    /**
     * Evolve a game of Life through its generations, emitting the state of the
     * game at each generation.  Evolution will continue until the universe
     * becomes static or until the given maximum number of generations is
     * reached.
     *
     * @param generations The number of generations to evolve.
     **/
    public void play (int generations, UserInterface ui) {

        // Show the initial state.
        ui.display();

        // Loop through the generations.
        for (_generation = 1; _generation <= generations; _generation += 1) {

            // Wait for approval for the next move.
            ui.triggerMove();

            // Evolve the game by one step and show it.
            evolve();
            ui.display();

        } // evolution loop

    } // play ()
    // =========================================================================



    // =========================================================================
    /**
     * Evolve the state of the game's universe from the current generation to
     * the next.
     **/
    public void evolve () {

        // WRITE ME
        // Our Game has 'Grid' and 'generation' as it's attributes
        // Evolve each cell in the grid based on the current state of the grid
        for(int row= 0; row < getRows(); row += 1){
            for(int column=0; column < getColumns(); column += 1){

                getCell(row, column).evolve();


            }//inner for loop
        }//outer for loop


        //once every cell has evolved - Advance to the next generation
        for(int row= 0; row < getRows(); row += 1){
            for(int column=0; column < getColumns(); column += 1){

                getCell(row, column).advance();


            }//inner for loop
        }//outer for loop


    } // evolve ()
    // =========================================================================



    // =========================================================================
    /**
     * Provide the current generation number.
     *
     * @return The current generation number for this universe.
     **/
    public int getGeneration () {

        return _generation;

    }
    // =========================================================================



    // =========================================================================
    /**
     * Provide the number of live cells.
     *
     * @return The number of live cells in the current universe.
     **/
    public int getPopulation () {

        // WRITE ME
        // Get the total number of Alive cells in the current grid

        int population = 0;

        for(int row= 0; row < getRows(); row += 1){
            for(int column=0; column < getColumns(); column += 1){

                if(getCell(row, column).isAlive())
                    population +=1;
            }
        }
        
        return population;

    } // getPopulation()
    // =========================================================================



    // =========================================================================
    /**
     * Provide the number of rows in the universe.
     *
     * @return The number of rows in the universe.
     **/
    public int getRows () {

        return _grid.getRows();

    } // getRows()
    // =========================================================================



    // =========================================================================
    /**
     * Provide the number of columns in the universe.
     *
     * @return The number of columns in the universe.
     **/
    public int getColumns () {

        return _grid.getColumns();

    } // getColumns()
    // =========================================================================



    // =========================================================================
    /**
     * Provide direct access to a <code>Cell</code> from this universe.
     *
     * @param row The row coordinate of the <code>Cell</code>.
     * @param column The column coordinate of the <code>Cell</code>.
     **/
    public Cell getCell (int row, int column) {

        return _grid.getCell(row, column);

    } // getCell()
    // =========================================================================



// =============================================================================
} // class Game
// =============================================================================
