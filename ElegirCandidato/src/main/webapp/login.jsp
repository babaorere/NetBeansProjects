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
    // verificar que venimos de una llamada post
    boolean marca = "Enviar".equals(((String) request.getParameter("submit")));

    // recuperamos los valores de los campos
    String strNickname = (String) request.getParameter("nickname");
    String strPassword = (String) request.getParameter("password");

    // validamos los campos
    boolean valNickname = (strNickname != null) && (strNickname.length() >= 4) && (strNickname.length() <= 16);
    boolean valPassword = (strPassword != null) && (strPassword.length() >= 4) && (strPassword.length() <= 16);
    boolean datosValidos = marca && valNickname && valPassword;

    // reinicializamos el campo, para que en caso de una reentrada, se mantenga el valor
    if (!valNickname) {
        strNickname = "";
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
        <h1 class="mycenter">Iniciar sesión</h1>

        <% if (datosValidos) {
                // si estamos en una reentrada post con datos validos, nos redirigimos a home
                Registro.login(strNickname, strPassword);
                response.sendRedirect("/ElegirCandidato");
            } else {%>

        <form method="post" action="/ElegirCandidato/login.jsp">

            <div>
                <label for="username">Nickname del Usuario</label>
                <div><input type="text" id="nickname" name="nickname" value="<%=strNickname%>" placeholder="Nickname"></div>
                    <% if (marca && !valNickname) { %>
                <p style="color:red;">El nickname debe tener una longitud >= 4, y <= 16</p>
                <% }%>
            </div>

            <br>
            <div>
                <label for="password">Password de Usuario</label>
                <div><input type="password" id="password" name="password" value="" placeholder="Password"></div>
                    <% if (marca && !valPassword) { %>
                <p style="color:red;">El password debe tener una longitud >= 4, y <= 16</p>
                <% }%>
            </div>

            <br>
            <div><input type="submit" id="submit" name="submit" value="Enviar"></div>

            <br>
            <div><a href="/ElegirCandidato/index.jsp">Home</a></div>
            <br>

        </form>

        <% }%>

    </body>
</html>