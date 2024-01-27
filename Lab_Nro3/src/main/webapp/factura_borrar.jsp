<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.webapp.lab_nro3.Factura" %>

<%  
    int numero;
    try {
        numero= Integer.parseInt(request.getParameter("numero"));
    }  catch(Exception e) { 
        numero= 0;
    }                
                
    if(numero > 0) {
        if(Factura.delete(numero) != 1) {
            numero= 0;
        }
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Borrar Factura</title>

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

        </style>

    </head>

    <body>
        <h1 class="mycenter">Borrar Factura</h1>
        <br>

        <% if(numero <= 0) { %>
        <p class="mycenter" style="color:red;">Factura no encontrada</p>
        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

        <% } else {         
        response.sendRedirect("factura_tabla.jsp");              
        } %>        
    </body>
</html>

