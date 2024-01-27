<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.webapp.elegircandidato.Registro" %>
<%@page import="com.webapp.elegircandidato.Candidato" %>
<%
// https://es.wikipedia.org/wiki/JavaServer_Pages    
// https://stackoverflow.com/questions/4967482/redirect-pages-in-jsp
// https://www.w3schools.com/howto/tryit.asp?filename=tryhow_css_cards2
// https://codepen.io/smashingmag/pen/WNoERXo
//https://www.freecodecamp.org/espanol/news/html-vs-body-como-configurar-el-ancho-y-el-alto-para-el-tamano-de-la-pagina-completa/
// https://lineadecodigo.com/html/imagen-con-enlace-en-html/
// https://freefrontend.com/css-cards/
%>

<%
// https://www.w3schools.com/howto/tryit.asp?filename=tryhow_css_cards2
// https://codepen.io/smashingmag/pen/WNoERXo
    // Verificar que hay un usuario registrado
    if (Registro.getNickname() == null) {
        // no hay usuario, ir a la pagina de login.jsp
        response.sendRedirect("/ElegirCandidato/login.jsp");
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
        <link rel="stylesheet" href="stylesheet.css">        
    </head>
    <body>
        <h1 class="mycenter">Tabla de Resultados</h1>
        <br>

        <table class="mytable mycenter">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Votos</th>
            </tr>

            <% for (Candidato reg : Candidato.getList()) {%>
            <tr>                    
                <td><%= reg.getId()%></td>
                <td><%= reg.getNombre()%></td>
                <td><%= reg.getVotos()%></td>
            </tr>
            <% }%>

        </table>  

        <br>
        <div><a href="index.jsp">Home</a></div>
        <br>

        <br>
    </body>
</html>
