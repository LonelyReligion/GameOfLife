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
    
    private static Session instance;
    
    /** Number of frames executed since the last start. */
    private Integer noFrames; 
    
    private Grid startingFormation;
    
    /**
     *  Zero-argument constructor 
     */
    private Session(){
        noFrames = 0;
        startingFormation = new Grid(); //2check!
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
    
    public void setStartingFormation(Grid newFormation){
        startingFormation = newFormation;
    };
    
    public Grid getStartingFormation(){
        return startingFormation;
    };
    
    public static synchronized Session getSession(){
        if(instance == null)
            instance = new Session();
        return instance;
    }
}
