/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.exception;

/**
 * Exception class for objects thrown when attempting to set invalid (0 or less) dimensions for the grid.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class InvalidDimensionsException extends Exception {
    /**
     * Non-parameter constructor
     */
    public InvalidDimensionsException() {
    }

    /**
     * Exception class constructor
     *
     * @param message display message
     */
    public InvalidDimensionsException(String message) {
        super(message);
    }
}

