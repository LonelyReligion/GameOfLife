/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pl.polsl.lab1.agnieszka.tazbirek.gameoflife;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.lab1.agnieszka.tazbirek.controller.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        
        GridController controller = new GridController(w, h);
        
        controller.updateView();
        
        System.out.print("Set the initial pattern by providing coordinates of live cells. \nWhen you are done simply type start.");
        Scanner scanner = new Scanner(System.in);
        boolean escape = false;
        while(!escape){
            System.out.print("\ny:");
            String input = scanner.next();
            if("start".equals(input)){
                escape = true;
            }else{
                int i = Integer.parseInt(input); //exception! 
                System.out.print("x:");
                input = scanner.next();
                if("start".equals(input)){
                    escape = true;
                } else{
                    int j = Integer.parseInt(input); //exception!
                    controller.setGridCellAlive(i, j, true);
                    controller.updateView();
                }
                System.out.print("\n");
            }
        }
        
        System.out.print("How many frames of simulation would you like played out for you?\n");
        String tmp = scanner.next();
        int howLong = Integer.parseInt(tmp); //exception!
        
        for(int i = 0; i < howLong; i++){
            controller.step();
            controller.clearView();
            controller.updateView();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

