/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.exception;

/**
 *
 * @author Agnieszka Tażbirek
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

