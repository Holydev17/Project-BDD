<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset ="UTF-8">
        <title> Confirmation</title>
    </head>
    <body>
        <h1>Confirmation</h1>
        
        <p> Type : ${sessionScope.typeTransaction}</p>
        <p>Chambre : ${sessionScope.numChambre}</p>
        <p>Prenom : ${sessionScope.prenom}</p>
        <p> nom : ${sessionScope.nom}</p>
        <p> date de debut : ${sessionScope.dateDeDebut} </p>
        <p> date de fin :${sessionScope.dateDeFin} </p>
    </body>
</html>