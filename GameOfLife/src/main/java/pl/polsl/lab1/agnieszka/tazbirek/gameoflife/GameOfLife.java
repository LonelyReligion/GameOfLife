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
     * @param args arguments
     */
    public static void main(String[] args) {
        View view = new View();
        
        String tmp = "";
        for(var argument : args){
            tmp += argument;
        }
        
        if("".equals(tmp))
        {
            Scanner scanner = new Scanner(System.in);
            view.print("What's your name?");
            tmp = scanner.next();     
        }
        view.print("Hello " + tmp);
        
        view.print("GUIDE");
        
        //will be in view
        view.print("SIZE OF THE GRID");
        Scanner scanner = new Scanner(System.in);
        view.print("HEIGHT OF THE GRID: ");
        
        //problem z wpisywaniem
/**
 *         int wysokosc = 0;
        tmp = scanner.next();
        if(tmp.matches("\\d")){
            wysokosc = Integer.parseInt(tmp);
        }    
        
        int szerokosc = 0;
        tmp = scanner.next();
        if(tmp.matches("\\d")){
            szerokosc = Integer.parseInt(tmp);
        }
        
        if(wysokosc != 0 && szerokosc != 0){
            Plansza nowa = new Plansza(wysokosc, szerokosc);
        }
 */
        
        view.print("SETTING INITIAL STRUCTURE");
        view.print("SIMULATION");
        view.print("ESC");
    }
}
