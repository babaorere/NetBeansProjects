	
<%@ page language='java' contentType="text/html" %>
<%! String nombre= "Nombre Alumno"; %>
<%! String cedula= "Cédula del Alumno"; %>
<%! String curso= "Curso del Alumno"; %>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Centrar Datos</title>

        <style>
            .mycenter {
                height: 400px;
                background-color: #EEE;
                display:flex;
                justify-content: center;
                align-items: center;
            }
        </style>

    </head>

    <body bgcolor="white">
        <div class="mycenter">
            Nombre Alumno: <%=nombre%> 
            <br>
            Cédula Alumno: <%=cedula%> 
            <br>
            Curso Alumno: <%=curso%> 
        </div>
    </body>
</html>