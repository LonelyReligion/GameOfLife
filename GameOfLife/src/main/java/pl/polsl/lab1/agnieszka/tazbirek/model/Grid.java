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
}
