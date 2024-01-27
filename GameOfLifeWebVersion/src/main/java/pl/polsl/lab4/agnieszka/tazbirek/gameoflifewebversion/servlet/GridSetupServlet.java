package pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.exception.InvalidDimensionsException;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.model.Cell;
import pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.model.Grid;

import static pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.model.Session.getSession;

/**
 * Servlet class taking care of adding live cells beforehand and simulating grid.
 * @author User
 */
@WebServlet(urlPatterns = {"/GridSetupServlet"})
public class GridSetupServlet extends HttpServlet {
    
    int LastLog = 0; /** to solve fetch last ID and increment */
    Connection con;
    
    public GridSetupServlet(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found");
        }
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app");
        } catch (SQLException ex) {
            Logger.getLogger(GridSetupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        createLog();
        createTables();
    }
    
    /** Game of life's playing field. */
    private Grid model = new Grid();
    
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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        String id = new String();
        
        /** Invalid dimensions */
        boolean error1 = false;
        
        /** Invalid coordinates */
        boolean error2 = false;
        
        /** Do we need to change the starting formation in session */
        boolean changeStartingFormation = false;
        
        HttpSession session = request.getSession(); 
        session.setAttribute("connection", con); 
        if(id.isEmpty()){
            id = session.getId();
        } else if(!id.equals(session.getId())){
                    // make a connection to DB
            try {
                Statement statement = con.createStatement();
                // Usuwamy dane z tabeli
                statement.executeUpdate("DROP Log");
                statement.executeUpdate("DROP COT");
                createLog();
                createTables();
            } catch (SQLException sqle) {
                System.err.println(sqle.getMessage());
            }
        }
                
        if(request.getParameter("goBack") != null){
                ;
        }
        else if(request.getParameter("simulate") != null){ /** step clicked */
            getSession().setNoFrames(getSession().getNoFrames()+1);
            model.step();  
        }else if(request.getParameter("formation")!=null) {
            /** scenario: setting the starting formation */
            try{
                model.setCellAlive(Integer.parseInt(request.getParameter("yPos")), Integer.parseInt(request.getParameter("xPos")), true);
                changeStartingFormation = true;
                getSession().setNoFrames(0);
   
                Cookie yCookie = new Cookie("lastY", request.getParameter("yPos"));
                response.addCookie(yCookie);
                Cookie xCookie = new Cookie("lastX", request.getParameter("xPos"));
                response.addCookie(xCookie);   
               
                insertData(Integer.parseInt(request.getParameter("xPos")), Integer.parseInt(request.getParameter("yPos")), "Log");
                insertData(model.countAliveCells());
                
                LastLog += 1;
            }catch(NumberFormatException exc){
                error2 = true;
            }
        }else{
            try{
                /** scenario: setting up grid's width and height */
                Integer width = Integer.valueOf(request.getParameter("width"));
                Integer height = Integer.valueOf(request.getParameter("height"));
                request.removeAttribute("height");
                request.removeAttribute("width");
                try{
                    model.setDims(height, width);
                    getSession().setNoFrames(0);
                }catch(InvalidDimensionsException ide){
                    error1 = true;
                }
            }
            catch(NumberFormatException ex){
                error1 = true;
            }
        }
        

        request.setAttribute("simulate", null);
        request.setAttribute("formation", null);

        String lastValue = "Recently added (implemented with cookies): ";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastX") || cookie.getName().equals("lastY")) {
                     lastValue += " " + cookie.getValue();
                }
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GridSetupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GridSetupServlet at " + request.getContextPath() + "</h1>");
            
            if(!error1 && !error2){
                String output = gridToString(model);
                if(changeStartingFormation){
                    getSession().setStartingFormation(output);
                }
                
                out.println("<p style=\"font-family: Serif\">" + output + "</p>");
                out.println("<p>Add live cells</p>");
                
                out.println("<p>" + lastValue + "</p>");
                
                out.println("<form action=\"GridSetupServlet\" method=\"POST\">");
                out.println("x: ");
                out.println("<input name=\"xPos\" type=\"text\" value=\"\" />");
                out.println(" y: ");
                out.println("<input name=\"yPos\" type=\"text\" value=\"\" />");
                out.println("<input type=\"submit\" name=\"formation\" value=\"Start\">");
                out.println("</form>");

                out.println("<p>or simulate</p>");

                out.println("<form action=\"GridSetupServlet\" method=\"POST\">");
                out.println("<input type=\"submit\" name=\"simulate\" value=\"Step\">");
                out.println("</form>");

                out.println("<br/>");

                out.println("<form action=\"GridInfoServlet\" method=\"POST\">");
                out.println("<input type=\"submit\" name=\"data\" value=\"Data\">");
                out.println("</form>");
            }else if(error1){
                out.println("<h3>Invalid dimensions error</h3>");
                out.println("<p>Go back and resubmit.</p>");
            }else if(error2){
                out.println("<h3>Invalid coordinates error</h3>");
                out.println("<p>Go back and resubmit.</p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
     * Returns string representation of provided grid
     * @param grid provided grid
     * @return string representation of provided grid
     */
    private String gridToString(Grid grid){
        String output = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"; //non-breaking space entity
        for(int j = 0; j < grid.getCells().get(0).size(); j++){
            output += (j + "&nbsp&nbsp");
        }
        output += ("<br/>");
        
        ArrayList<ArrayList<DeadOrAlive>> deadAliveValues = new ArrayList<>();
        for(int i = 0; i < grid.getCells().size(); i++){
            deadAliveValues.add(new ArrayList<>());
            for(Cell c : grid.getCells().get(i)){
                deadAliveValues.get(i).add(c.getAlive() ? DeadOrAlive.x : DeadOrAlive.o);
            }
        }
        
        for(int i = 0; i < deadAliveValues.size(); i++){
            int j = 0;
            output += (i + (i < 10 ? "&nbsp&nbsp&nbsp" : "&nbsp"));
            for(DeadOrAlive state : deadAliveValues.get(i)){
                output += (" " + state.toString() + (j > 9? "&nbsp&nbsp&nbsp" : "&nbsp"));
                j++;
            }
            output += ("<br/>");
        }
        return output;
    };
    
    private void createLog() {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Log "
                    + "(id INTEGER, x INTEGER, "
                    + "y INTEGER)");
            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    private void createTables() {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE COT " /** Cells over time */
                    + "(id INTEGER, totalLiveCells INTEGER )");
            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
        
    private void insertData(int x, int y, String name) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO " + name + " VALUES (" + LastLog + ", " + x + ", " + y + ")");
            System.out.println("Data inserted");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    private void insertData(int noCells) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO COT VALUES (" + LastLog + ", " + noCells + ")");
            System.out.println("Data inserted");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
        
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
