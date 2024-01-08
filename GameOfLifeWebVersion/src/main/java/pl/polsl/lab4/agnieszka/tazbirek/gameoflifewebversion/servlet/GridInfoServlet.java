package pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.servlet;



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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static pl.polsl.lab4.agnieszka.tazbirek.gameoflifewebversion.model.Session.getSession;

/**
 * Servlet class displaying information such as number of frames passed and how the grid looked before any steps.
 * @author Agnieszka Tażbirek
 * @version 1.0
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GridInfoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GridInfoServlet at " + request.getContextPath() + "</h1>");
            
            out.println("<p> Number of frames passed: " + getSession().getNoFrames() + "</p>");
            out.println("<h3>Starting formation:</h3>");
            out.println("<p>" + getSession().getStartingFormation() + "</p>");
            
            out.println("<form action=\"GridSetupServlet\" method=\"GET\">");
            out.println("<input type=\"submit\" name=\"goBack\" value=\"Go back\">");
            out.println("</form>");
            
            out.println("<p>" + selectCOT() + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
        public String selectCOT() {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            String tmp = new String(" ");
            return tmp;
        }
        // make a connection to DB
        try ( Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM COT");
            String tmp = new String();
            
            // Przeglądamy otrzymane wyniki
            tmp = "ID | Cells <br/>";
            while (rs.next()) {
                tmp += ("  " + rs.getInt("id") + "   " + rs.getString("totalLiveCells") + "<br/>");
            }
            tmp += "<br/>";
            rs.close();
            return tmp;
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return " ";
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
