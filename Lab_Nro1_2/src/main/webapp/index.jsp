	
<%@ page language='java' contentType="text/html" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Tabla del 1 al 100</title>

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

            tr:hover {
                background-color: #D6EEEE;
            }

        </style>

    </head>

    <body bgcolor="white">
        <div class="mycenter">

            <table class="mytable">
                <tr>
                    <th>  </th>
                        <% for(int i= 1; i <= 10; i++) { %>
                    <th><%= i %></th>
                        <% } %>
                </tr>

                <% int count= 1; %>
                <% for(int i= 1; i <= 10; i++) { %>

                <tr>                    
                    <td><%= i %></td>
                    <% for(int j= 1; j <= 10; j++) { %>                    
                    <td><%= count++ %></td>
                    <% } %>
                </tr>

                <% } %>

            </table>             

        </div>
    </body>
</html>