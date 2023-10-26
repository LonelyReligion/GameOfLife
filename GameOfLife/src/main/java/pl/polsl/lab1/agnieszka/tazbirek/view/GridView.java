/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;
import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;

/**
 *
 * @author User
 */

 /**
 * View class is managing displaying things on the monitor.
 */
public class GridView {
    public void printGrid(Cell[][] Cells){
        for(int i = 0; i < Cells.length; i++){
            for(int j = 0; j < Cells[i].length; j++){
                if(Cells[i][j].getAlive()){
                    System.out.print(" x ");
                }else{
                    System.out.print(" - ");
                };    
            }
            System.out.print("\n");
        }
    }

}
