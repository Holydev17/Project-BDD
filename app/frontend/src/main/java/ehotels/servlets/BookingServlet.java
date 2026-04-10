package ehotels.servlets;
import java.time.LocalDate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;


import ehotels.dao.ChambreDAO;
import ehotels.dao.ReservationDAO;
import ehotels.db.DBConnection;
import ehotels.models.Chambre;
import ehotels.models.Client;
import ehotels.models.Reservation;
import ehotels.dao.LocationDAO;
import ehotels.dao.ClientDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="BookingServlet", urlPatterns="/booking")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException{
       // format le text de la requete dans le jsp .
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        // les valeurs qui seront lu de la base de donnee 
        String idHotel  =  request.getParameter("idHotel");
        String nomHotel = request.getParameter("nomChaine");
        String villeHotel = request.getParameter("ville");
        String dateDeDebut = request.getParameter("dateDebut");
        String dateDeFin = request.getParameter("dateFin");
        String capacityMinimum = request.getParameter("capaciteMin");// Integer 
        String commoditeCh =request.getParameter("commodite");
        String prixMinimum = request.getParameter("prixMin");//double
        String prixMaximum = request.getParameter("prixMax");// double
        String trierPar = request.getParameter("triPar");
        String ordreDeTri = request.getParameter("ordre");
        String typeTransac = request.getParameter("typeTransaction");
        // envoyer au jsp les attributs de hotel à afficher 
        request.setAttribute("nomHotel", nomHotel);
        request.setAttribute("idHotel", idHotel);
        request.setAttribute("villeHotel", villeHotel);
        request.setAttribute("dateDeDebut",dateDeDebut);
        request.setAttribute("dateDeFin",dateDeFin);
        request.setAttribute("trierPar", trierPar);
        request.setAttribute("ordreDeTri",ordreDeTri);
        request.setAttribute("commodite", commoditeCh);
        request.setAttribute("typeTransaction", typeTransac);
        // verifie si les attributs prie dans le transfer qui sont dans la jsp
        if ((idHotel == null || idHotel.isEmpty()) || (nomHotel == null || nomHotel.isEmpty()) || (villeHotel == null || villeHotel.isEmpty())){
            RequestDispatcher rd = request.getRequestDispatcher("booking.jsp");
            rd.forward(request,response);
            // return pour envoyer la requete . est ce que j'ai bien compris ?
            return ;
        }
       

        // convertir pour utiliser dans la methode sql 
        int id_Hotel = Integer.parseInt(idHotel);
        Integer capacityMin = (capacityMinimum != null && !capacityMinimum.isEmpty())? Integer.parseInt(capacityMinimum):null;
        Double prixMin = (prixMinimum != null && !prixMinimum.isEmpty())? Double.parseDouble(prixMinimum):null;
        Double prixMax = (prixMaximum != null && !prixMaximum.isEmpty())? Double.parseDouble(prixMaximum):null;
        request.setAttribute("capacityMin",capacityMin);
        request.setAttribute("prixMin", prixMin);
        request.setAttribute("prixMax",prixMax);

        


        //on le declalre null ca r on ne peut pas l'instancier dans le try catch 
        Connection cons = null;

        try {
            cons = DBConnection.getConnection();
            ChambreDAO ch = new ChambreDAO(cons);
            //if ( ch.getChambresByHotel(id_Hotel) != null)// puis que mamethode ne prend pas 
            //correction methode de tri pour les chmbre
            List<Chambre> ChambresByIdHotelSorted = ch.rechercherChambresAvancee(dateDeDebut,dateDeFin,capacityMin,
            commoditeCh,prixMin,prixMax,trierPar,ordreDeTri);
            request.setAttribute("ChambresidHotelstrier", ChambresByIdHotelSorted);

            RequestDispatcher rd = request.getRequestDispatcher("booking.jsp");
            rd.forward(request,response);
        } catch (SQLException e) {
           e.printStackTrace();
           request.setAttribute("error", e.getMessage());
           RequestDispatcher rd = request.getRequestDispatcher("booking.jsp");
           rd.forward(request,response);

        }finally{
            // voir si la connexion a déjà été ouverte correction a  apporté aux 
            if (cons != null){
                try {
                    cons.close();     
                }catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String nasClient = request.getParameter("nasClient");
        String numero = request.getParameter("numeroDelarue");
        String rue = request.getParameter("rue");
        String ville = request.getParameter("ville");
        String province = request.getParameter("province");
        String typeTransaction = request.getParameter("typeTransaction");
        String dateDeDebut =  request.getParameter("dateDeDebut");
        String dateDeFin = request.getParameter("dateDeFin");
        String idHotel = request.getParameter("idHotel");
        String numChambre = request.getParameter("numChambre");

        


        if (prenom == null || nom == null || nasClient == null ||
            numero == null || rue == null || ville == null || province == null ||
            typeTransaction == null || dateDeDebut == null || dateDeFin == null ||
            idHotel == null || numChambre == null ){

            request.setAttribute("error","Champs manquants");
            doGet(request,response);

        }
        
            String adresse = numero + " "+ rue+" "+ ville +" "+province;
            int nas_client = Integer.parseInt(nasClient);
            int num_chambre = Integer.parseInt(numChambre);

            LocalDate dateDEDebut = LocalDate.parse(dateDeDebut);
            LocalDate dateDEFin = LocalDate.parse(dateDeFin);

            request.getSession().setAttribute("Type Transaction", typeTransaction);
            request.getSession().setAttribute("numChambre", num_chambre);
            request.getSession().setAttribute("prenom", prenom);
            request.getSession().setAttribute("nom",nom);
            request.getSession().setAttribute("dateDeDebut",dateDeDebut);
            request.getSession().setAttribute("dateDeFin",dateDeFin);
            


            Reservation reservation = new Reservation(nas_client,num_chambre,dateDEDebut,dateDEFin);

            Connection cons = null;
            try{
                ClientDAO dao = new ClientDAO(cons);
                Client existant = dao.getClientByNAS(nas_client);

                if (existant == null){
                    Client client = new Client(nas_client,prenom,nom,adresse);
                    dao.addClient(client);
                }

                cons = DBConnection.getConnection();
                if(typeTransaction.equals("reservation")){
                    ReservationDAO dao2 = new ReservationDAO(cons);
                    dao2.ajouterReservation(reservation);
                }else if (typeTransaction.equals("location")){
                    LocationDAO dao1 = new LocationDAO(cons);
                    dao1.ajouterLocation(nas_client,num_chambre,dateDeDebut,dateDeFin);
                }
                

                response.sendRedirect("/frontend-1.0/confirmation.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                doGet(request,response);
     
             }finally{
                 // voir si la connexion a déjà été ouverte correction a  apporté aux 
                 if (cons != null){
                     try {
                         cons.close();     
                    }catch (SQLException e) {
                         e.printStackTrace();
     
                     }
                 }
             }
        
            
            
        }
    }
    

