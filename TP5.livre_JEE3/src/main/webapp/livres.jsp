<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recherche des Livres</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="header.jsp" %>
    
    <div class="container">
        <div class="card">
            <div class="card-header">
                Recherche des Livres
            </div>
            <div class="card-body">
                <form action="chercher.do" method="get">
                    <label>Mot Clé</label>
                    <input type="text" name="motCle" value="${model.motCle}" />
                    <button type="submit" class="btn btn-primary">Chercher</button>
                </form>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Titre Livre</th>
                            <th>Auteur</th>
                            <th>Prix</th>
                            <th>Genre</th>
                            <th>NBpages</th>
                            <th>Suppression</th>
                            <th>Edition</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${model.livres}" var="l">
                            <tr>
                                <td>${l.idLivre}</td>
                                <td>${l.titreLivre}</td>
                                <td>${l.auteur}</td>
                                <td>${l.prix}</td>
                                <td>${l.genre}</td>
                                <td>${l.nbpages}</td>
                                <td>
                                    <a onclick="return confirm('Êtes-vous sûr ?')"
                                       href="supprimer.do?id=${l.idLivre }">Supprimer</a>
                                </td>
                                <td>
                                    <a href="editer.do?id=${l.idLivre }">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
