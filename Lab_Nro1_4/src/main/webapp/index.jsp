	
<%@ page language='java' contentType="text/html" %>

<% 
 String [][]datos= {{"Huawei P20", "Huawei", "01/11/2021", ""},
    {"iPhone X", "Apple", "01/16/2021", ""},
    {"Samsung S9", "Samsung", "01/16/2023", ""}
 };



%>


<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Tabla de Celulares</title>

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
                padding: 15px;
                border-spacing: 15px;

                border: 1px solid white;
                border-collapse: collapse;
            }

            th, td {
                border: 1px solid white;
                border-collapse: collapse;
                background-color: #96D4D4;
            }

        </style>

    </head>

    <body bgcolor="white">
        <div class="mycenter">

            <table class="mytable">
                <tr>
                    <th>Celular</th>
                    <th>Fabricante</th>
                    <th>Fecha de Lanzamiento</th>
                    <th>Imagen</th>                    
                </tr>


                <% for(int i= 0 ; i < datos.length; i++) { %>
                <tr>                    
                    <% for(int j= 0 ; j < datos[0].length; j++) { %>
                    <td><%= datos[i][j] %></td>
                    <% } %>
                </tr>
                <% } %>

            </table>             

        </div>
    </body>
</html>