package ehotels.servlets;

import ehotels.models.ChaineHotel;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name = "AcceuilServlet", urlPatterns="/acceuil")
public class AcceuilServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException , IOException{
        response.setContentType("text/html");

        /* forward la reponse vers le jsp avec requestDispatcher */
        RequestDispatcher rd = request.getRequestDispatcher("/acceuil.jsp");
        rd.forward(request,response);

        /* connexion direct vers la base de données  */
        List <ChaineHotel> chaineHotelList = new ArrayList<>();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException , IOException{

    }
    
}
