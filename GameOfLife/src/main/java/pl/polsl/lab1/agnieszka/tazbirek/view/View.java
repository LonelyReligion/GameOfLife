/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;

import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;
import pl.polsl.lab1.agnieszka.tazbirek.model.Session;

/**
 * View class managing displaying things on behalf of the GridController.
 * @author Agnieszka Tażbirek
 * @version 1.0
 */
public class View extends javax.swing.JFrame 
                  implements ActionListener{
    
    private static Grid model; /** Grid object we are viewing. */
    private static Session sesh; /** Session object that contains information about current instance of the game */
    
    private Timer timer; /** Timer for triggering event generating next frame. */
    private int delay = 1000; /** Delay specifies speed of the animation. */
    
    /**
     * Enum representing the state of a cell
     */
    public enum DeadOrAlive{
        /** means alive */
        x,
        /** means dead */
        o;
    }
    
    /**
     * Changes information displayed by GUI after we get dimensions.
     * @param height height of the Grid
     * @param width width of the Grid
     */
    public void dimensionsPosted(Integer height, Integer width){
            dimensionsPost.setEnabled(false); /** User cannot change dimensions after they've been set.*/
            printGrid();
            consolePost.setEnabled(true);
            startSim.setEnabled(true);
            consoleInput.setEnabled(true);
    }
            
    /**
     * Two-argument constructor of the class View
     * @param instance Grid object we are viewing.
     * @param session Session object that contains information about current instance of the game
     */
    public View(Grid instance, Session session) {
        model = instance;
        sesh = session;
        
        getContentPane().setBackground(Color.decode("#fffda3")); 
        
        initComponents();
        grid.setEditable(false);
        consoleInput.setEnabled(false);
        
        dimensionsPost.setActionCommand("dimensionsPosted");
        dimensionsPost.addActionListener(this);
        dimensionsPost.setMnemonic(KeyEvent.VK_D); /** as in dimensions */
        
        consolePost.setActionCommand("xyPosted");
        consolePost.addActionListener(this);
        consolePost.setMnemonic(KeyEvent.VK_C); /** as in coordinates */
        
        startSim.setActionCommand("simStarted");
        startSim.addActionListener(this);
        startSim.setMnemonic(KeyEvent.VK_S); /** as in start */
        
        stopSim.setActionCommand("simStopped");
        stopSim.addActionListener(this);
        stopSim.setMnemonic(KeyEvent.VK_S); /** as in stop */
        
        if(model.getWidth() != 0 && model.getHeight() != 0){
            dimensionsPost.setEnabled(false);
            printGrid();
            consolePost.setEnabled(true);
            startSim.setEnabled(true);
            consoleInput.setEnabled(true);
        }
        
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay); 
        timer.setCoalesce(true);
        timer.stop();
    }
    
    /**
     * Displays Grid in the grid JTextArea
     */
    public void printGrid(){
        String output = "    "; 
        for(int j = 0; j < model.getCells().get(0).size(); j++){
            output += (j + "  ");
        }
        output += ("\n");
        
        ArrayList<ArrayList<DeadOrAlive>> deadAliveValues = new ArrayList<>();
        for(int i = 0; i < model.getCells().size(); i++){
            deadAliveValues.add(new ArrayList<>());
            for(Cell c : model.getCells().get(i)){
                deadAliveValues.get(i).add(c.getAlive() ? DeadOrAlive.x : DeadOrAlive.o);
            }
        }
        
        for(int i = 0; i < deadAliveValues.size(); i++){
            int j = 0;
            output += (i + (i < 10 ? "  " : " "));
            for(DeadOrAlive state : deadAliveValues.get(i)){
                output += (" " + state.toString() + (j > 9? "  " : " "));
                j++;
            }
            output += ("\n");
        }
        
        grid.setText(output);
    }
    
    /** Method handling event of ActionEvent type.
    *	@param e incoming ActionEvent event
    */
    public void actionPerformed(ActionEvent e){
        if(sesh.getSimulationRunning()){  /** If simulation is running proceed to the next frame */
            model.step(); 
            frameNumber.setText(String.valueOf(sesh.getNoFrames()));
            sesh.setNoFrames(sesh.getNoFrames()+1);
            printGrid();
        }
                        
        if ("dimensionsPosted".equals(e.getActionCommand())) { /** Dimensions provided via spinners, confirmed by button */
            Integer width = (Integer) ySpinner.getValue();
            Integer height = (Integer) ySpinner.getValue();
            try{
                model.setDims(height, width);
                dimensionsPosted(height, width);
            } catch(final InvalidDimensionsException ex){
                String backup = consoleOutput.getText();
                consoleOutput.setText(backup+="\nInvalid dimensions. Try again.");
            } 
            
        }else if ("xyPosted".equals(e.getActionCommand())){ /** Coordinates of an alive Cell provided by JTextField and confirmed by button */
            String backup = consoleOutput.getText();
            consoleOutput.setText(backup+= "\n" + (String) consoleInput.getText());
            
            String[] strArr = ((String) consoleInput.getText()).split("\\s+");
            
            if(strArr.length == 2){ /** Valid data */
                var x = Integer.parseInt(strArr[0]);
                var y = Integer.parseInt(strArr[1]);
                model.setCellAlive(y, x, true); 
                printGrid(); 
            } else { /** Invalid data */
                backup = consoleOutput.getText();
                consoleOutput.setText(backup += "\nPlease provide both coordinates as integers at once.");
            }
            consoleInput.setText(""); /** clear */
        } else if ("simStarted".equals(e.getActionCommand())){ /** Simulation started */
            sesh.setNoFrames(0);
            startSim.setEnabled(false);
            stopSim.setEnabled(true);
            sesh.setSimulationRunning(true);
            consoleInput.setEnabled(false);
            consolePost.setEnabled(false);
            timer.start();
        } else if ("simStopped".equals(e.getActionCommand())){ /** Simulation stopped */
            startSim.setEnabled(true);
            stopSim.setEnabled(false);
            sesh.setSimulationRunning(false);
            consoleInput.setEnabled(true);
            consolePost.setEnabled(true);
            timer.stop();
        }
    }
    /**	
     *	Creating and showing GUI 
     */   
    public static void createAndShowGUI(){
                /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View(model, sesh).setVisible(true);
            }
        });
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xSpinner = new javax.swing.JSpinner();
        ySpinner = new javax.swing.JSpinner();
        dimensionsPost = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleOutput = new javax.swing.JTextArea();
        consoleInput = new javax.swing.JTextField();
        consolePost = new javax.swing.JButton();
        startSim = new javax.swing.JButton();
        instruction = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        grid = new javax.swing.JTextArea();
        yLabel = new javax.swing.JLabel();
        xLabel = new javax.swing.JLabel();
        stopSim = new javax.swing.JButton();
        frameLabel = new javax.swing.JLabel();
        frameNumber = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Game Of Life");
        setBackground(new java.awt.Color(204, 204, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFont(new java.awt.Font("Cambria", 3, 10)); // NOI18N
        setResizable(false);

        xSpinner.setName("xDim"); // NOI18N

        ySpinner.setName("yDim"); // NOI18N

        dimensionsPost.setBackground(new java.awt.Color(204, 255, 255));
        dimensionsPost.setForeground(new java.awt.Color(51, 51, 51));
        dimensionsPost.setText("POST");
        dimensionsPost.setName("postDim"); // NOI18N

        consoleOutput.setBackground(new java.awt.Color(255, 204, 255));
        consoleOutput.setColumns(20);
        consoleOutput.setForeground(new java.awt.Color(102, 51, 255));
        consoleOutput.setLineWrap(true);
        consoleOutput.setRows(5);
        consoleOutput.setText("Set the initial pattern by providing coordinates of live cells.");
        consoleOutput.setName("consoleOutput"); // NOI18N
        jScrollPane1.setViewportView(consoleOutput);

        consoleInput.setName("consoleInput"); // NOI18N

        consolePost.setBackground(new java.awt.Color(204, 255, 255));
        consolePost.setForeground(new java.awt.Color(51, 51, 51));
        consolePost.setText("POST");
        consolePost.setEnabled(false);
        consolePost.setName("consolePost"); // NOI18N

        startSim.setBackground(new java.awt.Color(204, 255, 255));
        startSim.setForeground(new java.awt.Color(51, 51, 51));
        startSim.setText("START");
        startSim.setEnabled(false);
        startSim.setName("startSim"); // NOI18N

        instruction.setText("Provide dimensions of the grid.");

        grid.setBackground(new java.awt.Color(204, 255, 255));
        grid.setColumns(20);
        grid.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        grid.setForeground(new java.awt.Color(0, 51, 255));
        grid.setRows(5);
        grid.setName("grid"); // NOI18N
        jScrollPane2.setViewportView(grid);

        yLabel.setText("y:");

        xLabel.setText("x:");

        stopSim.setBackground(new java.awt.Color(204, 255, 255));
        stopSim.setForeground(new java.awt.Color(51, 51, 51));
        stopSim.setText("STOP");
        stopSim.setEnabled(false);
        stopSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopSimActionPerformed(evt);
            }
        });

        frameLabel.setText("Frame:");

        frameNumber.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(frameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(frameNumber)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dimensionsPost, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(xLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(yLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(instruction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startSim)
                            .addComponent(consoleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(consolePost, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(stopSim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(frameLabel)
                        .addComponent(frameNumber))
                    .addComponent(instruction, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yLabel)
                            .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dimensionsPost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consoleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consolePost, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startSim)
                            .addComponent(stopSim)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopSimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stopSimActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField consoleInput;
    private javax.swing.JTextArea consoleOutput;
    private javax.swing.JButton consolePost;
    private javax.swing.JButton dimensionsPost;
    private javax.swing.JLabel frameLabel;
    private javax.swing.JLabel frameNumber;
    private javax.swing.JTextArea grid;
    private javax.swing.JLabel instruction;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton startSim;
    private javax.swing.JButton stopSim;
    private javax.swing.JLabel xLabel;
    private javax.swing.JSpinner xSpinner;
    private javax.swing.JLabel yLabel;
    private javax.swing.JSpinner ySpinner;
    // End of variables declaration//GEN-END:variables
}
