<%@ page language='java' contentType="text/html" %>
<%@page import="java.util.*" %>  
<%@page import="jakarta.persistence.EntityManager" %>
<%@page import="webapp.jpa.entities.Persona" %>
<%@page import="webapp.utils.JpaUtil" %>



<%
    EntityManager em = JpaUtil.getEntityManager();
//    List<Persona> list = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
//    list.forEach(System.out::println);
    em.close();    
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Tabla de Personas</title>

        <style>
            .mycenter {
                height: 400px;
                background-color: #EEE;
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

    <body bgcolor="white">

        <h1 class="mycenter">Tabla de Personas</h1>

        <div class="mycenter">

            <table class="mytable">
                <tr>
                    <th>ID</th>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                </tr>

                <% for(Persona reg: list) { %>

                <tr>                    
                    <td><%= reg.getIdpersona() %></td>
                    <td><%= reg.getNombres() %></td>
                    <td><%= reg.getApellidos() %></td>
                </tr>

                <% } %>

            </table>             

        </div>
    </body>
</html>