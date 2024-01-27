<%@page contentType="text/html" pageEncoding="UTF-8" %> 
<%@page import="java.util.*" %>  
<%@page import="com.webapp.lab_nro3.Factura" %>

<%
    List<Factura> list= Factura.getList();
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Facturas</title>

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

        <h1 class="mycenter">Tabla de Facturas</h1>
        <br>
        <ul><li><a href="factura_nuevo.jsp">Crear Factura</a></li></ul>
        <br>

        <table class="mytable mycenter">
            <tr>
                <th>Número</th>
                <th>Proveedor</th>
                <th>Fecha</th>
                <th>Articulo</th>
                <th>Cantidado</th>
                <th>Unidad</th>
                <th>ITBM</th>
                <th>Total</th>
                <th>     </th>
                <th>     </th>
            </tr>

            <% for(Factura factura: list) { %>
            <tr>                    
                <td><%= factura.getNumero() %></td>
                <td><%= factura.getProveedor() %></td>
                <td><%= factura.getFecha() %></td>
                <td><%= factura.getArticulo() %></td>
                <td><%= factura.getCantidad() %></td>
                <td><%= factura.getUnidad() %></td>
                <td><%= factura.getItbm() %></td>
                <td><%= factura.getTotal() %></td>
                <td><a href="factura_modificar.jsp?numero=<%= factura.getNumero() %>">Editar</a></td>  
                <td><a href="factura_borrar.jsp?numero=<%= factura.getNumero() %>">Borrar</a></td> 
            </tr>
            <% } %>

        </table>             


        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

    </body>
</html>
