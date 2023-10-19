/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

/**
 *
 * @author Agnieszka Tażbirek
 */
public class Grid {
    /**
     * Set of cells on 2D grid
     */
    private Cell[][] Komorki;
    
    /**
     * Two-argument constructor
     * @param height - height of the grid
     * @param width - width of the grid
     */
    public Grid(int height, int width){
        Komorki = new Cell[height][width];
    };
    
     /**
     * One-argument constructor 
     * Height and width default values are both set to 20.
     */
    public Grid(){
        Komorki = new Cell[20][20];
    };
    
}
