<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.time.LocalDate" %>
<%@page import="com.webapp.lab_nro3.Factura" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Factura</title>

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

        </style>

    </head>

    <body>
        <div class='container py-5 px-5'>

            <h1 class="mycenter">Nueva Factura</h1>
            <br>

            <%
                boolean marca= "true".equals(((String)request.getParameter("marca")));

                String strNumero= (String)request.getParameter("numero");
                if(strNumero == null) {
                    strNumero= "0";
                }
                
                String strProveedor= (String)request.getParameter("proveedor");
                if(strProveedor == null) {
                    strProveedor= "";
                }
                
                String strFecha= (String)request.getParameter("fecha");
                if(strFecha == null) {
                    strFecha= "";
                }
                
                String strArticulo= (String)request.getParameter("articulo");
                if(strArticulo == null) {
                    strArticulo= "";
                }
                
                String strCantidad= (String)request.getParameter("cantidad");
                if(strCantidad == null) {
                    strCantidad= "";
                }
                
                String strUnidad= (String)request.getParameter("unidad");
                if(strUnidad == null) {
                    strUnidad= "";
                }

                String strItbm= (String)request.getParameter("itbm");
                if(strItbm == null) {
                    strItbm= "";
                }
                
                String strTotal= (String)request.getParameter("total");
                if(strTotal == null) {
                    strTotal= "";
                }
                
                int numero= 0;
                boolean valNumero;
                try {
                    numero= Integer.parseInt(strNumero);
                    valNumero= true;
                }  catch(Exception e) { 
                    valNumero= false;
                }   
                
                boolean valProveedor= strProveedor.length() != 0;
                
                boolean valFecha;
                LocalDate fecha;
                try {
                    fecha= LocalDate.parse(strFecha);
                    valFecha= true;
                } catch(Exception e) {
                    fecha= null;
                    valFecha= false;
                }  
                
                boolean valArticulo= strArticulo.length() != 0;
                
                int cantidad;
                boolean valCantidad;
                try {
                    cantidad= Integer.parseInt(strCantidad);
                    valCantidad= true;
                }  catch(Exception e) { 
                    cantidad= 0;
                    valCantidad= false;
                }   
                
                boolean valUnidad= strUnidad.length() != 0;
                
                double itbm;
                boolean valItbm;
                try {
                    itbm= Double.parseDouble(strItbm);
                    valItbm= true;
                }  catch(Exception e) { 
                    itbm= 0;
                    valItbm= false;
                }   
                                
                double total;
                boolean valTotal;
                try {
                    total= Double.parseDouble(strTotal);
                    valTotal= true;
                }  catch(Exception e) { 
                    total= 0;
                    valTotal= false;
                }   
                
                boolean datosValidos= valNumero && valProveedor && valFecha && valArticulo
                        && valCantidad && valUnidad && valItbm && valTotal;
            
             if(marca && datosValidos) {                                 
                if (Factura.save(new Factura(numero, strProveedor, fecha, 
                    strArticulo, cantidad, strUnidad, itbm, total)) == 1) { 
                    response.sendRedirect("factura_tabla.jsp");              
                } else { %>
            <p style="color:red;">Error al tratar de guardar los datos</p>
            <br>
            <li><a href="index.jsp">Home</a></li>

            <% } 
             } else { %>

            <form action="#" method="post">
                <br>
                <div class="">
                    <label for="numero" class="">Número</label>
                    <input type="text" class="" id="numero" name="numero" placeholder="Número" readonly
                           value="0" >
                    <% if(marca && !valNumero) { %>
                    <p style="color:red;">El número no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="proveedor" class="">Proveedor</label>
                    <input type="text" class="" id="proveedor" name="proveedor" placeholder="Proveedor"
                           value=<%= strProveedor %>>
                    <% if(marca && !valProveedor) { %>
                    <p style="color:red;">El proveedor no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="fecha" class="">fecha</label>
                    <input type="date" class="" id="fecha" name="fecha" placeholder="Fecha"
                           value=<%= strFecha %>>
                    <% if(marca && !valFecha) { %>
                    <p style="color:red;">La Fecha no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="articulo" class="">Articulo</label>
                    <input type="text" class="" id="articulo" name="articulo" placeholder="Articulo"
                           value=<%= strArticulo %>>
                    <% if(marca && !valArticulo) { %>
                    <p style="color:red;">El articulo no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="cantidad" class="">Cantidad</label>
                    <input type="number" step="1" min="0" class="" id="cantidad" name="cantidad" placeholder="Cantidad"
                           value=<%= strCantidad %>>
                    <% if(marca && !valCantidad) { %>
                    <p style="color:red;">La cantidad no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="unidad" class="">Unidad</label>
                    <input type="text" class="" id="unidad" name="unidad" placeholder="Unidad"
                           value=<%= strUnidad %>>
                    <% if(marca && !valUnidad) { %>
                    <p style="color:red;">La Unidad puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="itbm" class="">ITBM</label>
                    <input type="number" step="0.01" min="0" class="" id="itbm" name="itbm" placeholder="ITBM"
                           value=<%= strItbm %>>
                    <% if(marca && !valItbm) { %>
                    <p style="color:red;">El ITBM no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="total" class="">Total</label>
                    <input type="number" step="0.01" min="0" class="" id="total" name="total" placeholder="total"
                           value=<%= strTotal %>>
                    <% if(marca && !valTotal) { %>
                    <p style="color:red;">El total no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class= "">
                    <input type="submit" class="" id="submit" name="submit" value="Guardar" >
                </div>

                <br>
                <ul><li><a href="index.jsp">Home</a></li></ul>

                <div><input type="hidden" id="marca" name="marca" value="true"></div>

            </form>

            <% } %>

        </div>
    </body>
</html>