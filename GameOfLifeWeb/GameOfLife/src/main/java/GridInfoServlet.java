/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import pl.polsl.gameoflife.model.Cell;
import pl.polsl.gameoflife.model.Grid;
import pl.polsl.gameoflife.model.Session;
import static pl.polsl.gameoflife.model.Session.getSession;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/GridInfoServlet"})
public class GridInfoServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GridInfoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GridInfoServlet at " + request.getContextPath() + "</h1>");
            
            out.println("<p> Number of frames passed: " + getSession().getNoFrames() + "</p>");
            out.println("<h3>Starting formation:</h3>");
            out.println("<p>" + gridToString(getSession().getStartingFormation()) + "</p>");
            
            out.println("<form action=\"GridSetupServlet\" method=\"POST\">");
            out.println("<input type=\"submit\" name=\"simulate\" value=\"Go back\">");
            out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private String gridToString(Grid grid){
        String output = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"; //non-breaking space entity
        for(int j = 0; j < grid.getCells().get(0).size(); j++){
            output += (j + "&nbsp&nbsp");
        }
        output += ("<br/>");
        
        ArrayList<ArrayList<GridSetupServlet.DeadOrAlive>> deadAliveValues = new ArrayList<>();
        for(int i = 0; i < grid.getCells().size(); i++){
            deadAliveValues.add(new ArrayList<>());
            for(Cell c : grid.getCells().get(i)){
                deadAliveValues.get(i).add(c.getAlive() ? GridSetupServlet.DeadOrAlive.x : GridSetupServlet.DeadOrAlive.o);
            }
        }
        
        for(int i = 0; i < deadAliveValues.size(); i++){
            int j = 0;
            output += (i + (i < 10 ? "&nbsp&nbsp&nbsp" : "&nbsp"));
            for(GridSetupServlet.DeadOrAlive state : deadAliveValues.get(i)){
                output += (" " + state.toString() + (j > 9? "&nbsp&nbsp&nbsp" : "&nbsp"));
                j++;
            }
            output += ("<br/>");
        }
        return output;
    };
    
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
