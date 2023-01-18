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

    // Verificar que hay un usuario registrado
    if (Registro.getNickname() == null) {
        // no hay usuario, ir a la pagina de login.jsp
        response.sendRedirect("login.jsp");
    }

%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Elegir Candidato - Login</title>    
        <link rel="stylesheet" href="stylesheet.css">
    </head>
    <body>

        <br>
        <h1 class="mycenter">Elegir el Candidato</h1>

        <br>
        <h3 class="mycenter">Presione sobre la foto del Candidato</h1>
        <br>

        <ul class="card-wrapper">
            <% for (Candidato reg : Candidato.getList()) {%>
            <li class="card">
                <a href="procesarVoto.jsp?id=<%=reg.getId()%>">
                    <% if ("M".equals(reg.getGenero())) {%>
                    <img src="img/avatarM.png" alt="Avatar" style="width:50%">
                    <%} else { %>
                    <img src="img/avatarF.png" alt="Avatar" style="width:50%">
                    <%}%>
                </a>
                <div class="container">
                    <a href="procesarVoto.jsp?id=<%=reg.getId()%>">
                        <h4><b><%=reg.getNombre()%></b></h4> 
                    </a>
                </div>
            </li>
            <% }%>        
        </ul>

        <br>
        <div><a href="/ElegirCandidato/index.jsp">Home</a></div>
        <br>

        </body>
        </html>