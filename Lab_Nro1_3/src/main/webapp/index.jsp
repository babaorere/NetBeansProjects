	
<%@ page language='java' contentType="text/html" %>

<%
  
class Calculadora {
    public double suma(double x, double y) { return x + y;}
    public double resta(double x, double y) { return x - y;}
    public double multiplicacion(double x, double y) { return x * y;}
    public double division(double x, double y) { return x / y;}
    public double modulo(double x, double y) { return x % y;}
}
    
Calculadora cal= new Calculadora();

double x= 3.33;
double y= 0-00;

%>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Tabla del 1 al 100</title>

        <style>
            .mydiv {
                height: 400px;
                background-color: #EEE;
                text-align:center;
            }


        </style>

    </head>

    <body bgcolor="white">
        <div class="mydiv">
            <h1 >Calculadora JSP</h1>
            <h2>Valor de X:= <%= x %></h2>            
            <h2>Valor de Y:= <%= y %></h2>
            <h2>Suma => <%= x %> + <%= y %> es: <%= cal.suma(x, y) %></h2>
            <h2>Resta => <%= x %> - <%= y %> es: <%= cal.resta(x, y) %></h2>
            <h2>Multiplicación => <%= x %> x <%= y %> es: <%= cal.multiplicacion(x, y) %></h2>
            <% if(y != 0) { %>
            <h2>División => <%= x %> / <%= y %> es: <%= cal.division(x, y) %></h2>
            <h2>Módulo => <%= x %> % <%= y %> es: <%= cal.division(x, y) %></h2>
            <% } else { %>
            <h2>División => <%= x %> / <%= y %> es: No es posible ejecutar</h2>            
            <h2>Módulo => <%= x %> % <%= y %> es: No es posible ejecutar</h2>
            <% } %>            
        </div>
    </body>
</html>