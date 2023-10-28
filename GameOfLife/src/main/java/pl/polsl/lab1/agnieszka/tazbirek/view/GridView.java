/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;
import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;

/**
 *
 * @author Agnieszka Tażbirek
 */

 /**
 * View class is managing displaying things on the monitor.
 */
public class GridView {
    
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
    
    public void clearView(){
        System.out.print("\n\n\n");
    };
    
    public void displayMessage(String message){
        System.out.print(message);
    };
}
