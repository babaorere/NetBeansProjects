<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.webapp.elegircandidato.Candidato" %>
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

<%
    // recuperamos los valores de los campos
    String strId = (String) request.getParameter("id");

    Candidato reg = null;
    boolean error = true;
    int id = -1;

    // convertimos el valor strint a entero
    try {
        id = Integer.parseInt(strId);
        reg = Candidato.getList().get(id);
        if (reg != null) {
            reg.setVotos();
            error = false;
        } else {
            error = true;
        }
    } catch (Exception e) {
        // error, regresamos a la pagina de votacion
        //response.sendRedirect("/ElegirCandidato/elegir.jsp");
        error = true;
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmar Voto</title>
        <link rel="stylesheet" href="stylesheet.css">        
    </head>
    <body>
        <br>
        <h1 class="mycenter">Confirmar Voto</h1>

        <br>
        <div>
            <% if (error) {%>
            <h3 style="color:red;">ERROR ERROR ERROR => id: <%=strId%></h3>            
            <%} else {%>
            <h3>Voto realizado al Candidato => <%=reg.getNombre()%></h3>            
            <%}%>
        </div>

        <br>
        <div><a href="/ElegirCandidato/index.jsp">Home</a></div>
        <br>

    </body>
</html>
