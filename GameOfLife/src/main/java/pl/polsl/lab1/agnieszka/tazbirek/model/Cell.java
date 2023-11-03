/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

/**
 * Class representing the cell on a grid in the game.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class Cell {
    /**
     * Binary value representing cell's state (1 - alive, 0 - dead)
     */
    boolean alive;
    
    /**
     * Zero-argument constructor setting alive field to false.
     */
    public Cell(){
        this.alive = false;
    };
    
    public Cell(boolean alive){
        this.alive = alive;
    };
    
    /**
     * Sets field alive to boolean value.
     * @param alive value that alive field will be set to
     */
    public void setAlive(boolean alive){
        this.alive = alive;
    };
    
    /**
     * Returns the value of alive field
     * @return value of alive field
     */
    public boolean getAlive(){
        return alive;
    };
}
