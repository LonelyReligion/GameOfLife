/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.view.View;
/**
 * Controller class for the Grid class from models. 
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class GridController {
    /** Grid object we are taking control of. */
    private Grid model;
    /** GridView we are using to display communicates. */
    private View view;  
    
    /**
     * Zero-argument constructor. Model and view are created as well.
     */
    public GridController(){
        this.model = new Grid();
        this.view = new View(model);
        view.createAndShowGUI();
    };
    
     /**
     * Sets dimensions for the grid.
     * @param height of the grid
     * @param width of the grid
     */
    public void setGridDims(Integer height, Integer width){
        try{
            model.setDims(height, width);
            view.dimensionsPosted(height, width);
        } catch(final InvalidDimensionsException e){
            System.out.print("exception occurred in controller \n");
        }
    };
}
