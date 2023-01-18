<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    // Simplemente reseteamos el usuario
    Registro.logout();

    // nos redirigimos al home
    response.sendRedirect("/ElegirCandidato");
%>