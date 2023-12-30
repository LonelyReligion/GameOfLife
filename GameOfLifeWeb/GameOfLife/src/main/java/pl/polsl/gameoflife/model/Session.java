/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.gameoflife.model;

/**
 * Class representing state of current instance of the Game.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class Session {
    /** Number of frames executed since the last start. */
    private Integer noFrames; 
    /** Is simulation currently running. */
    private boolean simulationRunning; 
    
    /**
     *  Zero-argument constructor 
     */
    public Session(){
        noFrames = 0;
        simulationRunning = false;
    }
    
    /**
     * Returns value of the noFrames field.
     * @return value of noFrames field 
     */
    public Integer getNoFrames(){
        return noFrames;
    };
    
    /**
     * Sets frames field.
     * @param frames value that the field will be set to.
     */
    public void setNoFrames(Integer frames){
        noFrames = frames;
    };
    
    /**
     * Sets simulationRunning field.
     * @param newValue value that the simulationRunning field will be set to.
     */
    public void setSimulationRunning(boolean newValue){
        simulationRunning = newValue;
    };
    
    /**
     * Returns value of the simulationRunning field.
     * @return value of the simulationRunning field
     */
    public boolean getSimulationRunning(){
        return simulationRunning;
    };
}
