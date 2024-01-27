	
<%@ page language='java' contentType="text/html" %>

<%
    double datos[] = {10.5, 30.2, 20.30, 40.1, 36.25, 35.99, 25.41};

    double mayor = datos[0];
    double menor = datos[0];

    for (int i = 1; i < datos.length; i++) {

        if (datos[i] > mayor) {
            mayor = datos[i];
        }

        if (datos[i] < menor) {
            menor = datos[i];
        }

    }

%>


<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Votación</title>

        <style>
            body {
                height: 400px;
                background-color: #EEE;
            }

            .mycenter {
                display:flex;
                justify-content: center;
            }


        </style>

    </head>

    <body bgcolor="white">
        <div class="">

            <div class="mycenter">
                <h1 >Mayor y Menor de Temperaturas JSP</h1>
                <br>
            </div>

            <div class="mycenter">
                <% for (int i = 0; i < datos.length; i++) {%>
                <%= datos[i]%>
                <% }%>
            </div>

            <div class="mycenter">
                <br>
                <h3>Mayor temperatura: <%= mayor%></h3> 
            </div>

            <div class="mycenter">
                <br>
                <h3>Menor temperatura: <%= menor%></h3> 
            </div>            
        </div>
    </body>
</html>