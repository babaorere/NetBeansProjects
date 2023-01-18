	
<%@ page language='java' contentType="text/html" %>
<% %>
<% %>
<% %>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>CRUD de Cliente y Factura</title>

        <style>
            body {
                height: 400px;
                background-color: #EEE;
            }

            .mycenter {
                display:flex;
                justify-content: center;
                align-items: center;
            }
        </style>

    </head>

    <body bgcolor="white">
        <div class="mycenter">
            <h1>CRUD de Cliente y Factura</h1>
        </div>

        <div class="mycenter">
            <ul>
                <li><a href="cliente_nuevo.jsp">Crear Cliente</a></li>
                <li><a href="cliente_tabla.jsp">Tabla de Clientes</a></li>              
                <br>
                <li><a href="factura_nuevo.jsp">Crear Factura</a></li>
                <li><a href="factura_tabla.jsp">Tabla de Facturas</a></li>              
            </ul>
        </div>
    </body>
</html>