/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.model.Session;
import pl.polsl.lab1.agnieszka.tazbirek.view.View;
/**
 * Controller class for the Grid class from models. 
 * @author Agnieszka Tażbirek
 * @version 1.1
 */
public class GridController {
    /** Grid object we are taking control of. */
    private final Grid model;
    /** GridView we are using to display communicates. */
    private final View view;  
    /** Session we are using to store our frames */
    private final Session sesh;
    
    /**
     * Zero-argument constructor. Model, Session and view are created as well. 
     */
    public GridController(){
        this.model = new Grid();
        this.sesh = new Session();
        this.view = new View(model, sesh);
        
        View.createAndShowGUI(); /** GUI is being created */
    };
    
     /**
     * Sets dimensions for the grid.
     * @param height height of the grid
     * @param width height of the grid
     */
    public void setGridDims(Integer height, Integer width){
        try{
            model.setDims(height, width);
            view.dimensionsPosted(height, width);
        } catch(final InvalidDimensionsException ex){
            /** user will provide these via GUI */
        } 
    };
}
