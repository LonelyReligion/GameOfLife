/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.view.GridView;
/**
 * Controller class for the Grid class from models. 
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class GridController {
    /** Grid object we are taking control of. */
    private Grid model;
    /** GridView we are using to display communicates. */
    private GridView view;  
    /** Number of frames user wants to display. */
    private int howLong;
    
    /**
     * Two-argument constructor for the class.
     * @param width - number of cells in every row
     * @param height - number of cells in each column
     */
    public GridController(int height, int width){
        this.model = new Grid(height, width);
        this.view = new GridView();
    };
    
    /**
     * Zero-argument constructor. Model and view are created as well.
     */
    public GridController(){
        this.model = new Grid();
        this.view = new GridView();
    };
    
    /**
     * Sets cell in the (j, i) position alive filed to passed boolean.
     * @param i  y position on the model grid
     * @param j  x position on the model grid
     * @param alive - the boolean value that the alive field of the cell will be set to.
     */
    public void setGridCellAlive(int i, int j, boolean alive){
        model.setCellAlive(i, j, alive);
    };
    
    /**
     * Shows the grid.
     */
    public void updateView(){
        view.printGrid(model.getCells()); 
    };
    
    /**
     * Clears the view.
     */
    public void clearView(){
        view.clearView();
    };
    
    /**
     * Applies the algorithm for deciding which cells are alive and which are not in the next gen.
     */
    public void step(){
        model.step();
    };
    
    /**
     * Sets dimensions for the grid.
     * @param height of the grid
     * @param width of the grid
     */
    public void setGridDims(int height, int width){
        try{
            model.setDims(height, width);
        } catch(final InvalidDimensionsException e){
            view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
        }
    };
    
    /**
     * Asks user for the desired dimensions of the grid.
     */
    public void setGridDims(){
        Scanner scanner = new Scanner(System.in);
        try{
            view.displayMessage("Width:");
            int height = Integer.parseInt(scanner.next());
            view.displayMessage("Height:");
            int width = Integer.parseInt(scanner.next());
            try{
                model.setDims(height, width);
            } catch(final InvalidDimensionsException e){
                view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
            }
        } catch(final NumberFormatException e){
            view.displayMessage("You provided invalid dimensions. Dimensions will not be changed.");
            view.displayMessage("Default dimensions are 20x20.");
        }
    };
    
    /**
     * Displays passed string on the screen.
     * @param message displayed string
     */
    public void displayMessage(String message){
        view.displayMessage(message);
    };
    
    /**
     * Asks user for the coordinates of live cells for the first stage until user writes start.  
     */
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
    
    /**
     * Executes howLong number of steps.
     */
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
    
    /**
     * Asks user how many frames should be displayed.
     */
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
