/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;
import java.util.ArrayList;
import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;

/**
 * This view class is managing displaying things on behalf of the GridController.
 * @author Agnieszka Tażbirek
 * @version 1.1
 */
public class GridView {
    /**
     * Enum representing the state of a cell
     */
    public enum DeadOrAlive{
        /** means alive */
        x,
        /** means dead */
        o;
    }
    
    /**
     * Prints Cells (live cells as x and dead as -) alongside axis labels. 
     * @param Cells - current grids Cells field
     */
    public void printGrid(ArrayList<ArrayList<Cell>> Cells){
        System.out.print("    "); 
        for(int j = 0; j < Cells.get(0).size(); j++){
            System.out.print(j + "  ");
        }
        System.out.print("\n");
        
        ArrayList<ArrayList<DeadOrAlive>> deadAliveValues = new ArrayList<>();
        for(int i = 0; i < Cells.size(); i++){
            deadAliveValues.add(new ArrayList<>());
            for(Cell c : Cells.get(i)){
                deadAliveValues.get(i).add(c.getAlive() ? DeadOrAlive.x : DeadOrAlive.o);
            }
        }
        
        for(int i = 0; i < deadAliveValues.size(); i++){
            int j = 0;
            System.out.print(i + (i < 10 ? "  " : " "));
            for(DeadOrAlive state : deadAliveValues.get(i)){
                System.out.print(" " + state.toString() + (j > 9? "  " : " "));
                j++;
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
