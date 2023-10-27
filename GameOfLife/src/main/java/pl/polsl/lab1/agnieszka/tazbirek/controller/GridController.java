/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.controller;

import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.view.GridView;
/**
 *
 * @author Agnieszka Tażbirek
 */
public class GridController {
    private Grid model;  
    private GridView view;  
    
    public GridController(int width, int height){
        this.model = new Grid(width, height);
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
}
