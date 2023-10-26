/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.lab1.agnieszka.tazbirek.gameoflife;

import java.util.Scanner;
import pl.polsl.lab1.agnieszka.tazbirek.view.*;
import pl.polsl.lab1.agnieszka.tazbirek.controller.*;
import pl.polsl.lab1.agnieszka.tazbirek.model.*;

/**
 *
 * @author Agnieszka Tażbirek
 */
public class GameOfLife {
    /**
     * Main loop of the project.
     * 
     * @param args height and width of a grid
     */
    public static void main(String[] args) {
        GridView view = new GridView();
        
        int w, h = 20;
        if(args.length >1){
            w = Integer.parseInt(args[0]); //exception!
            h = Integer.parseInt(args[1]); //exception!
        }
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Width:");
            w = Integer.parseInt(scanner.next()); //exception! 
            System.out.print("Height:");
            h = Integer.parseInt(scanner.next()); //exception!
        }
        System.out.print("\n");
        
        Grid model = new Grid(w, h);
        GridController controller = new GridController(model, view);
        
        controller.updateView();
    }
}
