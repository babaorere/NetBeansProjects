<%@page contentType="text/html" pageEncoding="UTF-8" %> 
<%@page import="java.util.*" %>  
<%@page import="com.webapp.lab_nro3.Cliente" %>

<%
    List<Cliente> list= Cliente.getList();
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Clientes</title>

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

        <h1 class="mycenter">Tabla de Clientes</h1>
        <br>
        <ul>
            <li><a href="cliente_nuevo.jsp">Crear Cliente</a></li>
        </ul>
        <br>

        <table class="mytable mycenter">
            <tr>
                <th>Número</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Dirección</th>
                <th>Teléfono</th>
                <th>Actividad</th>
                <th>     </th>
                <th>     </th>
                <th>     </th>
            </tr>

            <% for(Cliente cliente: list) { %>
            <tr>                    
                <td><%= cliente.getNumero() %></td>
                <td><%= cliente.getNombre() %></td>
                <td><%= cliente.getApellido() %></td>
                <td><%= cliente.getDireccion() %></td>
                <td><%= cliente.getTelefono() %></td>
                <td><%= cliente.getActividad() %></td>
                <td><a href="cliente_modificar.jsp?numero=<%= cliente.getNumero() %>">Editar</a></td>  
                <td><a href="cliente_borrar.jsp?numero=<%= cliente.getNumero() %>">Borrar</a></td> 
                <td><a href="cliente_facturas.jsp?numero=<%= cliente.getNumero() %>">Facturas</a></td> 
            </tr>
            <% } %>

        </table>             


        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

    </body>
</html>
