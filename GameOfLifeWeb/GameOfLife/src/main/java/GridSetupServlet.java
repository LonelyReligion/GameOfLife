/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Set;
import pl.polsl.gameoflife.exception.InvalidDimensionsException;

import pl.polsl.gameoflife.model.Cell;
import pl.polsl.gameoflife.model.Grid;
import pl.polsl.gameoflife.model.Session;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/GridSetupServlet"})
public class GridSetupServlet extends HttpServlet {
    private Grid model = new Grid();
    private Session sesh = new Session();
    
    /**
     * Enum representing the state of a cell
     */
    private enum DeadOrAlive{
        /** means alive */
        x,
        /** means dead */
        o;
    }
    
    private ArrayList<String> erros = new ArrayList<String>(); //?
    
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
                
        //request.setAttribute("Grid", model);
        //request.setAttribute("Session", sesh);
        
        try{
            //scenario: setting up grids width and height
            Integer width = Integer.valueOf(request.getParameter("width"));
            Integer height = Integer.valueOf(request.getParameter("height"));
            
            try{
                model.setDims(height, width);
            }catch(InvalidDimensionsException ide){
                ;
            }
        }
        catch(NumberFormatException ex){
            try {
                //scenario: setting the starting formation
                model.setCellAlive(Integer.valueOf(request.getParameter("yPos")), Integer.valueOf(request.getParameter("xPos")), true);
                sesh.setNoFrames(0);
            }catch(NumberFormatException exc){
                //either step clicked or site refreshed or wrong input
                String doWeSimulate = request.getParameter("simulate");
                if(doWeSimulate != null){ //step clicked
                    sesh.setNoFrames(sesh.getNoFrames()+1);
                    model.step();
                }
            }
        }
        
        String output = "&nbsp&nbsp&nbsp"; //non-breaking space entity
        for(int j = 0; j < model.getCells().get(0).size(); j++){
            output += (j + "  ");
        }
        output += ("<br/>");
        
        ArrayList<ArrayList<DeadOrAlive>> deadAliveValues = new ArrayList<>();
        for(int i = 0; i < model.getCells().size(); i++){
            deadAliveValues.add(new ArrayList<>());
            for(Cell c : model.getCells().get(i)){
                deadAliveValues.get(i).add(c.getAlive() ? DeadOrAlive.x : DeadOrAlive.o);
            }
        }
        
        for(int i = 0; i < deadAliveValues.size(); i++){
            int j = 0;
            output += (i + (i < 10 ? "&nbsp&nbsp" : "&nbsp"));
            for(DeadOrAlive state : deadAliveValues.get(i)){
                output += (" " + state.toString() + (j > 9? "&nbsp&nbsp" : "&nbsp"));
                j++;
            }
            output += ("<br/>");
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GridSetupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GridSetupServlet at " + request.getContextPath() + "</h1>");
            out.println("<p style=\"font-family: Arial\">" + output + "</p>");
            out.println("<p>Add live calls</p>");
            out.println("<form action=\"GridSetupServlet\" method=\"POST\">");
            out.println("x: ");
            out.println("<input name=\"xPos\" type=\"text\" value=\"1\" />");
            out.println(" y: ");
            out.println("<input name=\"yPos\" type=\"text\" value=\"1\" />");
            out.println("<input type=\"submit\" value=\"Start\">");
            out.println("</form>");
            out.println("<p>or simulate</p>");
            
            out.println("<form action=\"GridSetupServlet\" method=\"POST\">");
            out.println("<input type=\"submit\" name=\"simulate\" value=\"Step\">");
            out.println("</form>");
            
            out.println("<form action=\"GridInfoServlet\" method=\"POST\">");
            out.println("<input type=\"submit\" name=\"data\" value=\"Step\">");
            out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
        }
        
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("GridInfoServlet.java");
        reqDispatcher.forward(request, response);
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
