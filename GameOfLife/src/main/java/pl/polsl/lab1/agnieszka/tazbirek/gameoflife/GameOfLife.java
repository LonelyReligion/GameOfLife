/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.lab1.agnieszka.tazbirek.gameoflife;

import pl.polsl.lab1.agnieszka.tazbirek.controller.*;


/**
 * Main class of the application realizing the console implementation of Conway's Game of Life. 
 * @author Agnieszka Tażbirek
 * @version 1.1
 */
public class GameOfLife {
    /**
     * Main loop of the project.
     * 
     * @param args first argument - height of a grid, second argument - width of a grid
     */
    public static void main(String[] args) {
        GridController controller = new GridController();
        
        
        if(args.length >1){
            try {
                controller.setGridDims(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
            }catch(final NumberFormatException e){
                
            }
        }
        
    }
}

