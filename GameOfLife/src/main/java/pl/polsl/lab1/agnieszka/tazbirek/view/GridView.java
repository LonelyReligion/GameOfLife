/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;
import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;

/**
 * This view class is managing displaying things on behalf of the GridController.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class GridView {
    /**
     * Prints Cells (live cells as x and dead as -) alongside axis labels. 
     * @param Cells - current grids Cells field
     */
    public void printGrid(Cell[][] Cells){
        System.out.print("    ");
        for(int j = 0; j < Cells[0].length; j++){
            System.out.print(j + "  ");
        }
        System.out.print("\n");
        
        for(int i = 0; i < Cells.length; i++){
            if(i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
            for(int j = 0; j < Cells[i].length; j++){
                if(Cells[i][j].getAlive()){
                    if(j > 9)
                        System.out.print(" x  ");
                    else
                        System.out.print(" x ");
                }else{
                    if(j > 9)
                        System.out.print(" -  ");
                    else
                        System.out.print(" - ");
                };    
            }
            System.out.print("\n");
        }
    };
    
    /**
     * Prints three new lines for readability.
     */
    public void clearView(){
        System.out.print("\n\n\n");
    };
    
    /**
     * Prints provided string.
     * @param message - string to be printed
     */
    public void displayMessage(String message){
        System.out.print(message);
    };
}
