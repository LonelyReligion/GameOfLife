/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

/**
 *
 * @author Agnieszka Tażbirek
 */
public class Grid {
    /**
     * Set of cells on 2D grid
     */
    private Cell[][] Cells;
    
    /**
     * Two-argument constructor
     * @param height - height of the grid
     * @param width - width of the grid
     */
    public Grid(int height, int width){
        Cells = new Cell[height][width];
        
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[i].length; j++){
                Cells[i][j] = new Cell();
            };
        };
        
    }; 
    
    public Cell[][] getCells(){
        return Cells;
    };
    
    public void setCellAlive(int i, int j, boolean alive){
       if(i < Cells.length && j < Cells[0].length){
            Cells[i][j].setAlive(alive);
       };
    };
    
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
    }
    
    public int getNumberOfNeighbors(int i, int j){
        int count = 0; 
        for(int col = i - 1; col <= i+1; col++){
            for(int row = j - 1; row <= j+1; row++){
                boolean inBounds = !(col < 0) && !(row < 0) && col != Cells[0].length && row != Cells.length;
                boolean notTheSame = (row != j || col != i);
                if( inBounds && notTheSame && Cells[col][row].getAlive()){
                    count++;
                }
            }
        }
        return count;
    };
}
