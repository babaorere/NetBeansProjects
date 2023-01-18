<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.webapp.lab_nro3.Cliente" %>

<%  
    int numero;
    try {
        numero= Integer.parseInt(request.getParameter("numero"));
    }  catch(Exception e) { 
        numero= 0;
    }                
                
    if(numero > 0) {
        if(Cliente.delete(numero) != 1) {
            numero= 0;
        }
    }

%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Borrar Cliente</title>
        <style>
            * {
                margin: 0;
                padding: 0;
            }

            body {
                height: auto;
                background-color: #EEE;
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

            tr:hover {
                background-color: #D6EEEE;
            }


        </style>

    </head>

    <body>
        <h1 class="mycenter">Borrar Cliente</h1>
        <br>

        <% if(numero <= 0) { %>
        <%= numero %>
        <p class="mycenter" style="color:red;">Cliente no encontrado</p>
        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

        <% } else {
        response.sendRedirect("cliente_tabla.jsp");              
        } %>        
    </body>
</html>

