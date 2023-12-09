/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.model;

import java.util.ArrayList;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;

/**
 * Test class for methods in Grid class
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class GridTest {
    
    public GridTest() {
    }
    
    /**
     * Function providing arguments for {@link #testStep(java.util.ArrayList, java.util.ArrayList) testStep}.
     * @return stream of arguments for {@link #testStep(java.util.ArrayList, java.util.ArrayList) testStep} method
     */
    private static Stream<Arguments> listProvider(){
        return Stream.of(
            /** valid value 
             *  oxo    ooo
             *  oxo -> xxx
             *  oxo    ooo
             */
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
            ),
            /** valid value 
             * oooo    oooo
             * oxxo    oxxo
             * oxxo -> oxxo
             * oooo    oooo
             */
      arguments(
            new ArrayList<ArrayList<Cell>>(){{
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell()); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell()); add(new Cell());}});
            }},
            new ArrayList<ArrayList<Cell>>(){{
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell()); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell(true)); add(new Cell(true)); add(new Cell());}});
                add(new ArrayList<Cell>(){{add(new Cell()); add(new Cell()); add(new Cell()); add(new Cell());}});
            }}
            )
        );
    }
    /**
     * Test of step method, of class Grid.
     * @param Cells input grid normally provided by user or an array modified by a step function
     * @param ExpectedCells expected values after the transformation
     */
    @ParameterizedTest
    @MethodSource("listProvider")
    public void testStep(ArrayList<ArrayList<Cell>> Cells, ArrayList<ArrayList<Cell>> ExpectedCells) {
        //given
        Grid instance = new Grid(Cells.size(), Cells.get(0).size());
        //when
        instance.setCells(Cells);
        instance.step();
        
        //then
        for(int i = 0; i < Cells.size(); i++){
            for(int j = 0; j < Cells.get(0).size(); j++){
                assertEquals(ExpectedCells.get(i).get(j).getAlive(), instance.getCells().get(i).get(j).getAlive());
            }
        }
    }

    /**
     * Test of setDims method, of class Grid. 
     * @param width Width of the grid
     * @param height Height of the grid
     * @throws pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException
     */
    @ParameterizedTest
    @CsvSource({"5,5" /** valid value */, "0,0"  /** invalid limit value */, "-1,-1" /** invalid value */})
    public void testSetDims(int width, int height) throws InvalidDimensionsException {
        //given
        Grid instance = new Grid();
        if(width <= 0 || height <=0){
            //when
            InvalidDimensionsException exception = assertThrows(InvalidDimensionsException.class,
                    ()->instance.setDims(height, width));
            //then
            assertEquals("The Grid cannot have a dimension that has a value of 0 or less.", exception.getMessage());
        } else {
            //when
            instance.setDims(height, width);
            //then
            assertAll("Check dimensions",
                    ()->assertEquals(height, instance.getHeight()),
                    ()->assertEquals(width, instance.getWidth())
            );
        }
    }
    
}
