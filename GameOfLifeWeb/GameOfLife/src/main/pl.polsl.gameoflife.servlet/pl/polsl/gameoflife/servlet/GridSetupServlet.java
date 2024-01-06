package pl.polsl.gameoflife.servlet;

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
import static pl.polsl.gameoflife.model.Session.getSession;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/GridSetupServlet"})
public class GridSetupServlet extends HttpServlet {
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
                
        boolean error1 = false;
        boolean error2 = false;
        boolean changeStartingFormation = false;

        if(request.getParameter("goBack") != null){
                ;
        }
        else if(request.getParameter("simulate") != null){ //step clicked
            getSession().setNoFrames(getSession().getNoFrames()+1);
            model.step();  
        }else if(request.getParameter("formation")!=null) {
            //scenario: setting the starting formation
            try{
                model.setCellAlive(Integer.parseInt(request.getParameter("yPos")), Integer.parseInt(request.getParameter("xPos")), true);
                changeStartingFormation = true;
                getSession().setNoFrames(0);
   
                Cookie yCookie = new Cookie("lastY", request.getParameter("yPos"));
                response.addCookie(yCookie);
                Cookie xCookie = new Cookie("lastX", request.getParameter("xPos"));
                response.addCookie(xCookie);   
               
            }catch(NumberFormatException exc){
                error2 = true;
            }
        }else{
            try{
                //scenario: setting up grids width and height
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

        String lastValue = "Recently added: ";
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
