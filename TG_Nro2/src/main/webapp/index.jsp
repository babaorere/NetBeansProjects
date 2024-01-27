	
<%@ page language='java' contentType="text/html" %>
<%@page import="java.util.*" %>  
<%@page import="com.webapp.tg_nro2.Postulante" %>

<% 
    // crear unos postulantes de prueba
    List<Postulante> list= Postulante.obtenerLista();    
%>
<% %>
<% %>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Postulantes</title>

        <style>
            body {
                height: auto;
                background-color: #66ff66;
            }

            .mycenter {
                display:flex;
                justify-content: center;
                align-items: center;
            }

            .mytable {
                background-color: #000033;
                padding: 5px;
                border-spacing: 5px;
            }
        </style>

    </head>

    <body>

        <h1 class="mycenter">Tabla de Postulantes</h1>
        <br>

        <table class="mytable mycenter">
            <tr>
                <th>Cédula</th>
                <th>Nombre</th>
                <th>     </th>
            </tr>

            <% for(Postulante reg: list) { %>
            <tr>                    
                <td><%= reg.getCedula() %></td>
                <td><%= reg.getNombre() %></td>
                <td><a href="/TG_Nro2/resultado?id=<%= reg.getId() %>">Resultados</a></td> 
            </tr>
            <% } %>

        </table>             

        <br>
    </body>
</html>