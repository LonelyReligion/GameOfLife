/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pl.polsl.lab1.agnieszka.tazbirek.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.NumberFormatter;
import java.beans.*;
import java.util.ArrayList;
import pl.polsl.lab1.agnieszka.tazbirek.exception.InvalidDimensionsException;

import pl.polsl.lab1.agnieszka.tazbirek.model.Cell;
import pl.polsl.lab1.agnieszka.tazbirek.model.Grid;

/**
 * TODO: 
 * -printowanie grida po jego zawartosci za pomoca enuma
 * -post console podlaczyc do entera
 * @author user
 */
public class View extends javax.swing.JFrame 
                  implements ActionListener{
    
    private static Grid model;
    private static boolean simulationRunning = false;
    private Timer timer;
    private int delay = 1000;
    
    /**
     * Enum representing the state of a cell
     */
    public enum DeadOrAlive{
        /** means alive */
        x,
        /** means dead */
        o;
    }
    
    public void dimensionsPosted(Integer height, Integer width){
        System.out.print("dims posted " + width + " " + height + "\n");
        try{
            model.setDims(height, width);
            dimensionsPost.setEnabled(false);
            printGrid();
            consolePost.setEnabled(true);
            startSim.setEnabled(true);
            consoleInput.setEnabled(true);
            System.out.print("all have been executed \n");
        } catch(final InvalidDimensionsException ex){
            System.out.print("exception occurred \n");
            String backup = consoleOutput.getText();
            consoleOutput.setText(backup+="\nInvalid dimensions. Try again.");
        } 
    }
            
    /**
     * Creates new form View
     */
    public View(Grid instance) {
        model = instance;
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
        
        timer = new Timer(delay, this);
        timer.setInitialDelay(delay); 
        timer.setCoalesce(true);
        timer.stop();
    }
    
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
    
    public void actionPerformed(ActionEvent e){
        if(simulationRunning){ 
            model.step(); 
            frameNumber.setText(String.valueOf(Integer.parseInt(frameNumber.getText()) + 1));
            printGrid();
        }
                        
        if ("dimensionsPosted".equals(e.getActionCommand())) {
            Integer width = (Integer) ySpinner.getValue();
            Integer height = (Integer) ySpinner.getValue();
            
            dimensionsPosted(height, width);
            
        }else if ("xyPosted".equals(e.getActionCommand())){
            String backup = consoleOutput.getText();
            consoleOutput.setText(backup+= "\n" + (String) consoleInput.getText());
            
            String[] strArr = ((String) consoleInput.getText()).split("\\s+");
            
            if(strArr.length == 2){
                var x = Integer.parseInt(strArr[0]);
                var y = Integer.parseInt(strArr[1]);
                model.setCellAlive(y, x, true); 
                printGrid(); 
            } else {
                backup = consoleOutput.getText();
                consoleOutput.setText(backup += "\nPlease provide both coordinates as integers at once.");
            }
            consoleInput.setText("");
        } else if ("simStarted".equals(e.getActionCommand())){
            startSim.setEnabled(false);
            stopSim.setEnabled(true);
            simulationRunning = true;
            consoleInput.setEnabled(false);
            consolePost.setEnabled(false);
            timer.start();
        } else if ("simStopped".equals(e.getActionCommand())){
            startSim.setEnabled(true);
            stopSim.setEnabled(false);
            simulationRunning = false;
            consoleInput.setEnabled(true);
            consolePost.setEnabled(true);
            timer.stop();
        };
    }
        
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
                new View(model).setVisible(true);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        grid = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        stopSim = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        frameNumber = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(210, 215, 223));

        xSpinner.setName("xDim"); // NOI18N

        ySpinner.setName("yDim"); // NOI18N

        dimensionsPost.setBackground(new java.awt.Color(204, 243, 255));
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

        consolePost.setBackground(new java.awt.Color(204, 204, 255));
        consolePost.setForeground(new java.awt.Color(102, 102, 102));
        consolePost.setText("POST");
        consolePost.setEnabled(false);
        consolePost.setName("consolePost"); // NOI18N

        startSim.setBackground(new java.awt.Color(205, 237, 253));
        startSim.setForeground(new java.awt.Color(102, 102, 102));
        startSim.setText("START");
        startSim.setEnabled(false);
        startSim.setName("startSim"); // NOI18N

        jLabel1.setText("Provide dimensions of the grid.");

        grid.setBackground(new java.awt.Color(204, 255, 255));
        grid.setColumns(20);
        grid.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        grid.setForeground(new java.awt.Color(0, 51, 255));
        grid.setRows(5);
        grid.setName("grid"); // NOI18N
        jScrollPane2.setViewportView(grid);

        jLabel2.setText("y:");

        jLabel3.setText("x:");

        stopSim.setBackground(new java.awt.Color(153, 255, 255));
        stopSim.setForeground(new java.awt.Color(51, 51, 51));
        stopSim.setText("STOP");
        stopSim.setEnabled(false);
        stopSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopSimActionPerformed(evt);
            }
        });

        jLabel4.setText("Frame:");

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
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(frameNumber)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dimensionsPost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(startSim)
                                    .addGap(118, 118, 118)
                                    .addComponent(stopSim))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(consoleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(consolePost))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(frameNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dimensionsPost)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consoleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consolePost))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startSim)
                            .addComponent(stopSim)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopSimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stopSimActionPerformed

    /**
    public static void main( String[] arg) { 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    } 
*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField consoleInput;
    private javax.swing.JTextArea consoleOutput;
    private javax.swing.JButton consolePost;
    private javax.swing.JButton dimensionsPost;
    private javax.swing.JLabel frameNumber;
    private javax.swing.JTextArea grid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton startSim;
    private javax.swing.JButton stopSim;
    private javax.swing.JSpinner xSpinner;
    private javax.swing.JSpinner ySpinner;
    // End of variables declaration//GEN-END:variables
}
