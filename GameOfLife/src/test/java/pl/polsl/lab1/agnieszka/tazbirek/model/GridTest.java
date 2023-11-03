/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author User
 */
public class GridTest {
    private Grid grid;
    
    public GridTest() {
    }
    
    @BeforeEach
    public void setUp() {
        grid = new Grid();
    }

    /**
     * Test of step method, of class Grid.
     */
    @Test
    public void testStep() {
        System.out.println("step");
        Grid instance = new Grid();
        instance.step();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
