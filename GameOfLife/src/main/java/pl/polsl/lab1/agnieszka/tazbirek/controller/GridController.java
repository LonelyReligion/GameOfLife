/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;
import pl.polsl.lab1.agnieszka.tazbirek.gameoflife.GameOfLife;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.view.GridView;
/**
 *
 * @author Agnieszka Tażbirek
 */
public class GridController {
    private Grid model;  
    private GridView view;  
    private int howLong;
    
    public GridController(int width, int height){
        this.model = new Grid(width, height);
        this.view = new GridView();
    };
    
    public GridController(){
        this.model = new Grid();
        this.view = new GridView();
    };
        
    public void setGridCellAlive(int i, int j, boolean alive){
        model.setCellAlive(i, j, alive);
    };
    
    public void updateView(){
        view.printGrid(model.getCells()); 
    };
    
    public void clearView(){
        view.clearView();
    };
    
    public void step(){
        model.step();
    };
    
    public void setGridDims(int w, int h){
        try{
            model.setDims(w, h);
        } catch(final InvalidDimensionsException e){
            view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
        }
    };
    
    public void setGridDims(){
        Scanner scanner = new Scanner(System.in);
        try{
            view.displayMessage("Width:");
            int w = Integer.parseInt(scanner.next());
            view.displayMessage("Height:");
            int h = Integer.parseInt(scanner.next());
            try{
                model.setDims(w, h);
            } catch(final InvalidDimensionsException e){
                view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
            }
        } catch(final NumberFormatException e){
            view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
            view.displayMessage("Default dimensions are 20x20.");
        }
    };
    
    public void displayMessage(String message){
        view.displayMessage(message);
    };
    
    public void setGridInitialPattern(){
        view.displayMessage("Set the initial pattern by providing coordinates of live cells. \nWhen you are done simply type start.");
        Scanner scanner = new Scanner(System.in);
        boolean escape = false;
        while(!escape){
            view.displayMessage("\ny:");
            String input = scanner.next();
            if("start".equals(input)){
                escape = true;
            }else{
                try{
                    int i = Integer.parseInt(input); //exception! 
                    view.displayMessage("x:");
                    input = scanner.next();
                    if("start".equals(input)){
                        escape = true;
                    } else{
                        int j = Integer.parseInt(input); //exception!
                        model.setCellAlive(i, j, true);
                        view.printGrid(model.getCells());
                    }
                } catch(final NumberFormatException e){
                    view.displayMessage("You provided incorrect information. Try again.");
                }
                view.displayMessage("\n");
            }
        }
    };
    
    public void play(){
            for(int i = 0; i < howLong; i++){
            model.step();
            view.clearView();
            view.printGrid(model.getCells());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                view.displayMessage("An InterruptedException is thrown when a thread is interrupted while it's waiting, sleeping, or otherwise occupied.");
            }
        }
    };
    
    public void simulationSettings(){
        Scanner scanner = new Scanner(System.in);
        view.displayMessage("How many frames of simulation would you like played out for you?\n");
        String tmp = scanner.next();
        try {
            howLong = Integer.parseInt(tmp); //exception!
        } catch(final NumberFormatException e){
            view.displayMessage("You provided incorrect information. Number of frames was set to default - 15.");
            howLong = 15;
        }
    };
}
