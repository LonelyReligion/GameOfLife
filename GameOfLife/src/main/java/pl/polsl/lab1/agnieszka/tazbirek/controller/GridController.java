/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.view.GridView;
/**
 *
 * @author User
 */
public class GridController {
    private Grid model;  
    private GridView view;  
    
    public GridController(Grid model, GridView view){
        this.model = model;
        this.view = view;
    };
    
    public void updateView(){
        view.printGrid(model.getCells()); 
    };
}
