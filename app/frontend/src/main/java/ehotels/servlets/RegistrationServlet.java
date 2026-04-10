package ehotels.servlets;

import ehotels.models.ChaineHotel;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import ehotels.dao.ReservationDAO;
import ehotels.db.DBConnection;
import ehotels.models.Reservation;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name = "RegistrationServlet", urlPatterns="/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException , IOException{
        // formatter l'affichage jsp 
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html");

        String nas = request.getParameter("nasClient");
        // apres le if faire un return sinon j'ai une erreur 
        if (nas == null || nas.isEmpty()) {
            // On forward vers reservation.jsp sans mettre de reservations dans la requête
            RequestDispatcher rd = request.getRequestDispatcher("/reservation.jsp");
            rd.forward(request, response);
            return;
        }

        int nasNumber = Integer.parseInt(nas);

        /* connexion direct vers la base de données / correction avec copilot  */
        // al connexion avant ferme la base de données 
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            ReservationDAO dao = new ReservationDAO(conn);
           
            List<Reservation> reservations = dao.getReservationsByClient(nasNumber);
            // mettre les attibuts dont j'ai besoins dans la reponse dans le jsp.
            request.setAttribute("reservations", reservations);

            /* forward la reponse vers le jsp avec requestDispatcher */
            RequestDispatcher rd = request.getRequestDispatcher("/reservation.jsp");
            rd.forward(request, response);
        } catch (SQLException e) {// ajout du rd forward pour que la page de la requet s'affiche .
            e.printStackTrace();
            request.setAttribute("erreur", e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/reservation.jsp");
            rd.forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();  // fermer la connection 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException , IOException{

    }
    
}