/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.model;

/**
 * Class representing state of current instance of the Game. Made in accordance with singleton design pattern.
 * @author Agnieszka Tażbirek
 * @version 1.1
 */
public class Session {
    
    /**
     * Private instance of the class that can only be accessed with getSession() method.
     */
    private static Session instance;
    
    /** Number of frames executed since the last start. */
    private Integer noFrames; 
    
    /** 
     * String representing formation before any steps. o means cell is alive, x means it is dead.
     */
    private String startingFormation;
    
    /**
     *  Private zero-argument constructor 
     */
    private Session(){
        noFrames = 0;
        startingFormation = new String(); 
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
     * Sets startingFormation field.
     * @param newFormation value that the field will be set to.
     */
    public void setStartingFormation(String newFormation){
        startingFormation = newFormation;
    };
    
    /**
     * Returns value of the startingFormation field.
     * @return value of the startingFormation field
     */
    public String getStartingFormation(){
        return startingFormation;
    };
    
    /**
     * Thread safe variant.
     * @return instance of the class
     */
    public static synchronized Session getSession(){
        if(instance == null)
            instance = new Session(); /** If it doesn't already exist it's constructed here! */
        return instance;
    }
}
