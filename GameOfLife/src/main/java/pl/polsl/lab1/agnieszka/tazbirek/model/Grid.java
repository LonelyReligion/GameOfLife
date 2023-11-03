/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;
import java.util.ArrayList;
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
    private ArrayList<ArrayList<Cell>> Cells = new ArrayList<>();
    private int Height = 20; //are they even needed
    private int Width = 20;
     /**
     * Zero-argument constructor 
 Height and Width default values are both set to 20.
     */
    public Grid(){
        for(int i = 0; i < Height; i++){
            Cells.add(new ArrayList<>());
            for(int j = 0; j < Width; j++){
                Cells.get(i).add(new Cell());
            }
        }
    };  
        
    /**
     * Two-argument constructor
     * @param height Height of the grid
     * @param width Width of the grid
     */
    public Grid(int height, int width){
        this.Height = height;
        this.Width = width;
        
        for(int i = 0; i < height; i++){
            Cells.add(new ArrayList<>());
            for(int j = 0; j < width; j++){
                Cells.get(i).add(new Cell());
            }
        }
        
    }; 
    
    /**
     * Returns value of Cells field.
     * @return value of Cells field
     */
    public ArrayList<ArrayList<Cell>> getCells(){
        return Cells;
    };
    
    /**
     * Sets cell in the (j, i) position alive filed to passed boolean.
     * @param i y position on the model grid
     * @param j x position on the model grid
     * @param alive - the boolean value that the alive field of the cell will be set to.
     */
    public void setCellAlive(int i, int j, boolean alive){
       if(i < Height && j < Width){
            Cells.get(i).get(j).setAlive(alive);
       }
    };
    
    /**
     * Applies the algorithm for deciding which cells are alive and which are not in the next gen.
     */
    public void step(){
        ArrayList<ArrayList<Integer>> neighbors_array = new ArrayList<>();
        for(int i = 0; i < Height; i++){
            neighbors_array.add(new ArrayList<>());
            for(int j = 0; j < Width; j++){
                neighbors_array.get(i).add(this.getNumberOfNeighbors(i, j));
            }   
        }
        for(int i = 0; i < Height; i++){ //I will not add for each here, because I would still need to have i defined, because of
            for(int j = 0; j < Width; j++){
               int neighbors = neighbors_array.get(i).get(j); //here!
               if(Cells.get(i).get(j).getAlive() && (neighbors < 2 || neighbors > 3)){
                    Cells.get(i).get(j).setAlive(false);
               }else if(!Cells.get(i).get(j).getAlive() && neighbors == 3) {
                    Cells.get(i).get(j).setAlive(true);
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
                boolean inBounds = !(row < 0) && !(col < 0) && col != Width && row != Height;
                boolean notTheSame = (col != j || row != i);
                if( inBounds && notTheSame && Cells.get(row).get(col).getAlive()){
                    count++;
                }
            }
        }
        return count;
    };
    
    /**
     * Sets dimensions for the grid.
     * @param width Width of the grid
     * @param height Height of the grid
     * @throws pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException when dimensions are lesser or equal 0
     */
    public void setDims(int height, int width) throws InvalidDimensionsException {
        if(height <= 0 || width <= 0){
            throw new InvalidDimensionsException("The Grid cannot have a dimension that has a value of 0 or less.");
        }
        this.Height = height;
        this.Width = width;
        Cells = new ArrayList<>();
        
        for(int i = 0; i < height; i++){
            Cells.add(new ArrayList<Cell>());
            for(int j = 0; j < width; j++){ //for each here would cause ConcurrentModificationException
                Cells.get(i).add(new Cell());
            }
        }
    };
    
    public int getWidth(){
        return Width;
    }
    
    public int getHeight(){
        return Height;
    }
    
    public void setCells( ArrayList<ArrayList<Cell>> Cells ){
        this.Cells = Cells;
    }

}
