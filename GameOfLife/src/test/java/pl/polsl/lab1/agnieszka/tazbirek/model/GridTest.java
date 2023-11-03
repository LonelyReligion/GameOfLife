/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.platform.commons.util.StringUtils;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;
import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;

/**
 *
 * @author user
 */
public class GridTest {
    
    public GridTest() {
    }
    
    private static Stream<Arguments> listProvider(){
        return Stream.of(
            arguments(
            new ArrayList<ArrayList<Cell>>(){{
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell());}});
            }},
            new ArrayList<ArrayList<Cell>>(){{
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell(true)); add(new Cell(true)); add(new Cell(true));}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell());}});
            }}
            )
        );
    }
    /**
     * Test of step method, of class Grid.
     */
    @ParameterizedTest
    @MethodSource("listProvider")
    public void testStep(ArrayList<ArrayList<Cell>> Cells, ArrayList<ArrayList<Cell>> ExpectedCells) {
        System.out.println("step");
        
        Grid instance = new Grid(Cells.size(), Cells.get(0).size());
        instance.setCells(Cells);
        instance.step();
        //ExpectedCells, instance.getCells()
        
        for(int i = 0; i < Cells.size(); i++)
            for(int j = 0; j < Cells.get(0).size(); j++)
                assertEquals(ExpectedCells.get(i).get(j).getAlive(), instance.getCells().get(i).get(j).getAlive());
        
    }

    /**
     * Test of setDims method, of class Grid. 
     * @param width
     * @param height
     * @throws pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException
     */
    @ParameterizedTest
    @CsvSource({"5,5", "0,0", "-1,-1"})
    public void testSetDims(int width, int height) throws InvalidDimensionsException {
        System.out.println("setDims");
        Grid instance = new Grid();
        if(width <= 0 || height <=0){
            InvalidDimensionsException exception = assertThrows(InvalidDimensionsException.class,
                    ()->instance.setDims(height, width));
            assertEquals("The Grid cannot have a dimension that has a value of 0 or less.", exception.getMessage());
        } else {
            //shoud have propper dims
            instance.setDims(height, width);
            
            assertAll("Check dimensions",
                    ()->assertEquals(height, instance.getHeight()),
                    ()->assertEquals(width, instance.getWidth())
            );
        }
    }
    
}
