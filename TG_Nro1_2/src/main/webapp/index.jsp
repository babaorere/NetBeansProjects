	
<%@ page language='java' contentType="text/html" %>

<% 
 String candidatos[]= {"A", "B", "C", "D"};
 int votos[][]= {{194,48,206,45}, {180,20,320,16},
    {221,90,140,20},{432,50,821,14},{820,61,946,18}};
 
int total= 0;
int suma[]= {0, 0, 0, 0};

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
        <div class="">

            <div class="mycenter">
                <h1 >Elecciones JSP</h1>
                <br>
            </div>

            <div class="mycenter">
                <table class="mytable ">
                    <tr>
                        <th>Distrito</th>
                        <th>Candidato</th>
                        <th>Candidato</th>
                        <th>Candidato</th>
                        <th>Candidato</th>
                    </tr>

                    <tr>                    
                        <td></td>
                        <% for(int i= 0 ; i < candidatos.length; i++) { %>
                        <td><%= candidatos[i] %></td>
                        <% } %>
                    </tr>

                    <% total= 0;
                   for(int i= 0 ; i < votos.length; i++) { %>
                    <tr>                    
                        <td><%= (i + 1) %></td>
                        <% for(int j= 0 ; j < votos[0].length; j++) { %>
                        <td><%= votos[i][j] %></td>
                        <% suma[j]+= votos[i][j];
                           total+= votos[i][j]; 
                            } %>
                    </tr>
                    <% } %>

                    <td></td>
                    <% for(int i= 0 ; i < suma.length; i++) { %>
                    <td><%= (Math.round(suma[i] * 10000.00 / total) / 100.00) %>%</td> 
                    <% } %>

                </table>

            </div>

            <div class="mycenter">
                <br>
                <h3>Total de Votos: <%= total %></h3> 
            </div>

            <div class="mycenter">
                <%  int idx= 0;
                    double mayor= suma[idx];
                    
                    for(int i= 1 ; i < suma.length; i++) { 
                        if(suma[i] > mayor) {
                            idx= i;
                            mayor= suma[i];
                        }
                    }
                    
                    double porc= Math.round(suma[idx] * 10000.00 / total) / 100.00;
                
                %>

                <br>

                <% if(porc > 50.00) { %>
                <h3>El candidato ganador es  => <%= candidatos[idx] %></h3> 
                <% } else { %>
                <h3 style="color:red;">Es necesario ir a una segunda vuelta</h3> 
                <% } %>
            </div>

        </div>
    </body>
</html>