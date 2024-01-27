	
<%@ page language='java' contentType="text/html" %>
<%@page import="java.util.*" %>  
<%@page import="com.webapp.elegircandidato.Registro" %>

<%
// https://es.wikipedia.org/wiki/JavaServer_Pages    
// https://stackoverflow.com/questions/4967482/redirect-pages-in-jsp
// https://www.w3schools.com/howto/tryit.asp?filename=tryhow_css_cards2
// https://codepen.io/smashingmag/pen/WNoERXo
//https://www.freecodecamp.org/espanol/news/html-vs-body-como-configurar-el-ancho-y-el-alto-para-el-tamano-de-la-pagina-completa/
// https://lineadecodigo.com/html/imagen-con-enlace-en-html/
// https://freefrontend.com/css-cards/
%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Elegir Candidato</title>
        <link rel="stylesheet" href="stylesheet.css">
    </head>
    <body>
        <br>
        <h1 class="mycenter">Elegir Candidato</h1>

        <% if (Registro.getNickname() != null) {
        %>
        <br>
        <h3>Operador: <%=Registro.getNickname()%></h3>
        <% }%>

        <br>
        <ul>
            <li><a href="login.jsp">Login del Operador</a></li>
            <li><a href="logout.jsp">Logout</a></li>
            <br>           
            <li><a href="elegir.jsp">Elegir</a></li>
            <br>
            <li><a href="resultados.jsp">Resultados</a></li>            
        </ul>
        <br>
        <div><a href="index.jsp">Home</a></div>
        <br>    
    </body>
</html>
