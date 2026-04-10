<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!-- le prefixe c c'est pour toutes les balise Jstl-->
<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Reservation</title>
    </head>
    <body>
        <h1>Reserver une chambre</h1>
        <!-- action pointe vers la servlet et non la jsp-->
        <!-- les donnee ne s'afficher pas apres registration lorsque la demande valider je test /frontend-1.0/registration-->
        <form method="get" action="/frontend-1.0/registration">

            <label for="numero d'assurance sociale client">Numéro NAS</label>
            <input type="number" id="nasClient" name="nasClient">

        <!-- boutton pour afficher les reservations -->
        <button type="submit"> "afficher les reservations" </button>

        
        </form>
        <!-- boucle pour afficher toutes les reservations pour le nasclient  -->
        <c:if test="${not empty reservations}">
            <c:forEach var="r" items="${reservations}">
                <li>${r.idReservation}-${r.dateDebut}-${r.dateFin}-${r.statut}-${r.chambre}-${r.client}</li>
            </c:forEach>
        </c:if>

        <c:if test="${not empty erreur}">
            <p style="color:red">${erreur}</p>
        </c:if>

    </body>
    

</html>