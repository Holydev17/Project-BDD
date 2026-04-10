<p%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!-- le prefixe c c'est pour toutes les balise Jstl-->

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title> ${nomHotel} - ${ville}</title>
    </head>
    <body>
        <h1> Selectionner une chambre </h1>

         <form method="get" action="/frontend-1.0/booking" >
            <input type="hidden" name="idHotel" value="${idHotel}">
            <input type="hidden" name="nomChaine" value="${nomHotel}">
            <input type="hidden" name="ville" value="${villehotel}">
            
            <label for=" date de debut reservation/ location"> date de debut </label>
            <input type="date" id="dateDeDebut" name="${dateDeDebut}" >

            <label for=" date de fin reservation/ location "> date de fin</label>
            <input type="date" id="dateDeFin" name="${dateDeFin}">

            <label for="capacite minimum"> chosissez la capcite minimal</label>
            <input type="number" id="capacityMin" name="${capacityMin}">

            <label for="prix minimal"> prix minimum</label>
            <input type="number" id="prixMin" name="${prixMin}">

            <label for="prix maximal"> prix maximum</label>
            <input type="number" id="prixMax" name="${prixMax}">

            <select name="ordre">
                <option value="asc">Prix croissant</option>
                <option value="dsc">Prix decroissant</option>
            </select>
            <button type="submit">Filtrer</button>
            
        </form>
        <!-- afficher les chambres selon les citeres de tri -->
         <c:if test="${not empty ChambresidHotelstrier}">
            <c:forEach var="ch" items="${ChambresidHotelstrier}">

                <p>chambre ${ch.numChambre}-
                    ${ch.prix}-
                    ${ch.capacite} lit(s)-
                    ${ch.vue}-
                    ${ch.commodite}
                </p>
                
                 
            
        

                <!-- post les valeurs doivent etre les memes pour name  | la balise input ne se ferme pas avec input-->
        <form method="post" action="/frontend-1.0/booking">
                    <input type="hidden" name="idHotel" value="${idHotel}">

                    <input type="radio" name="typeTransaction" value="location">Location
                    <input type="radio" name="typeTransaction" value="Reservation"> Reservation
        
                    <label For="prenom du client"> entré votre prenom </label>
                    <input type="text" id="prenom" name="prenom">
        
                    <label For="nom du client"> entrée votre nom</label>
                    <input type="text" id="nom" name="nom">
        
                    <label For="numero d'assurance sociale du client">numéro de Nas</label>
                    <input type="number" id="nasClient" name="nasClient">

                    <label For="numero de la rue ">numero de la rue</label>
                    <input type="number" id="numero" name="numero">

                    <label For="numero de la rue ">rue</label>
                    <input type="text" id="rue" name="rue">

                    <label For="numero de la rue ">ville</label>
                    <input type="text" id="ville" name="ville">

                    <label For="numero de la rue ">province</label>
                    <input type="text" id="province" name="province">
                    <!-- button pour la selection de la chambre -->
        
                    <button type="sumbit" > choisir cette chambre</button>
        
        </form>
    </c:forEach>
    </c:if>
                
    </body>
</html>