/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;

/**
 * Class representing games universe - two-dimensional grid of square cells.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class Grid {
    /**
     * Set of cells on 2D grid
     */
    private Cell[][] Cells;
    
     /**
     * Zero-argument constructor 
     * Height and width default values are both set to 20.
     */
    public Grid(){
        Cells = new Cell[20][20];
        
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[i].length; j++){
                Cells[i][j] = new Cell();
            }
        }
    };  
        
    /**
     * Two-argument constructor
     * @param height height of the grid
     * @param width width of the grid
     */
    public Grid(int height, int width){
        Cells = new Cell[height][width];
        
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[i].length; j++){
                Cells[i][j] = new Cell();
            }
        }
        
    }; 
    
    /**
     * Returns value of Cells field.
     * @return value of Cells field
     */
    public Cell[][] getCells(){
        return Cells;
    };
    
    /**
     * Sets cell in the (j, i) position alive filed to passed boolean.
     * @param i y position on the model grid
     * @param j x position on the model grid
     * @param alive - the boolean value that the alive field of the cell will be set to.
     */
    public void setCellAlive(int i, int j, boolean alive){
       if(i < Cells.length && j < Cells[0].length){
            Cells[i][j].setAlive(alive);
       }
    };
    
    /**
     * Applies the algorithm for deciding which cells are alive and which are not in the next gen.
     */
    public void step(){
        int[][] neighbors_array = new int[Cells.length][Cells[0].length];
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[0].length; j++){
                neighbors_array[i][j] = this.getNumberOfNeighbors(i, j);
            }   
        }
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[0].length; j++){
               int neighbors = neighbors_array[i][j];
               if(Cells[i][j].getAlive() && (neighbors < 2 || neighbors > 3)){
                    Cells[i][j].setAlive(false);
               }else if(!Cells[i][j].getAlive() && neighbors == 3) {
                    Cells[i][j].setAlive(true);
               }
            }
        }
    };
    
    /**
     * Returns number of live neighbours of (j, i) cell on the grid.
     * @param i y position on the model grid
     * @param j x position on the model grid
     * @return number of live neighbours
     */
    private int getNumberOfNeighbors(int i, int j){
        int count = 0; 
        for(int row = i - 1; row <= i+1; row++){
            for(int col = j - 1; col <= j+1; col++){
                boolean inBounds = !(row < 0) && !(col < 0) && col != Cells[0].length && row != Cells.length;
                boolean notTheSame = (col != j || row != i);
                if( inBounds && notTheSame && Cells[row][col].getAlive()){
                    count++;
                }
            }
        }
        return count;
    };
    
    /**
     * Sets dimensions for the grid.
     * @param width width of the grid
     * @param height height of the grid
     * @throws pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException when dimensions are lesser or equal 0
     */
    public void setDims(int height, int width) throws InvalidDimensionsException {
        if(height <= 0 || width <= 0){
            throw new InvalidDimensionsException("The Grid cannot have a dimension that has a value of 0 or less.");
        }
        Cells = new Cell[height][width];
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[i].length; j++){
                Cells[i][j] = new Cell();
            }
        }
    };
}
