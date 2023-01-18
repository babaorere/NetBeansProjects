<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="com.webapp.lab_nro3.Factura" %>

<%  
    int numero;
    try {
        numero= Integer.parseInt(request.getParameter("numero"));
    }  catch(Exception e) { 
        numero= 0;
    }                
                
    Factura factura= Factura.get(numero);  

    boolean marca= "true".equals(((String)request.getParameter("marca")));
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Modificar Factura</title>

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
        <h1 class="mycenter">Modificar Factura</h1>
        <br>

        <% if(factura == null) { %>
        <%= numero %>
        <p class="mycenter" style="color:red;">Factura no encontrada</p>
        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

        <% } else { %>
        <div class='container py-5 px-5'>

            <%
                String strNumero;
                String strProveedor;
                String strFecha;
                String strArticulo;
                String strCantidad;
                String strUnidad;
                String strItbm;
                String strTotal;
                
                if(marca) {
                    strNumero= (String)request.getParameter("numero");
                    if(strNumero == null) {
                      strNumero= "0";
                    }
                
                    strProveedor= (String)request.getParameter("proveedor");
                    if(strProveedor == null) {
                        strProveedor= "";
                    }
                
                    strFecha= (String)request.getParameter("fecha");
                    if(strFecha == null) {
                        strFecha= "";
                    }
                
                    strArticulo= (String)request.getParameter("articulo");
                    if(strArticulo == null) {
                        strArticulo= "";
                    }
                
                    strCantidad= (String)request.getParameter("cantidad");
                    if(strCantidad == null) {
                        strCantidad= "";
                    }
                
                    strUnidad= (String)request.getParameter("unidad");
                    if(strUnidad == null) {
                        strUnidad= "";
                    }

                    strItbm= (String)request.getParameter("itbm");
                    if(strItbm == null) {
                        strItbm= "";
                    }
                
                    strTotal= (String)request.getParameter("total");
                    if(strTotal == null) {
                        strTotal= "";
                    }
                                                    
                } else {
                    strNumero= String.valueOf(factura.getNumero());
                    if(strNumero == null) {
                      strNumero= "0";
                    }
                
                    strProveedor= factura.getProveedor();
                    if(strProveedor == null) {
                        strProveedor= "";
                    }
                
                    strFecha= DateTimeFormatter.ofPattern("yyyy-MM-dd").format(factura.getFecha());
                    if(strFecha == null) {
                        strFecha= "";
                    }
                    
                
                    strArticulo= factura.getArticulo();
                    if(strArticulo == null) {
                        strArticulo= "";
                    }
                
                    strCantidad= String.valueOf(factura.getCantidad());
                    if(strCantidad == null) {
                        strCantidad= "";
                    }
                
                    strUnidad= factura.getUnidad();
                    if(strUnidad == null) {
                        strUnidad= "";
                    }

                    strItbm= String.valueOf(factura.getItbm());
                    if(strItbm == null) {
                        strItbm= "";
                    }
                
                    strTotal= String.valueOf(factura.getTotal());
                    if(strTotal == null) {
                        strTotal= "";
                    }                              
                }

                numero= 0;
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
                if (Factura.update(new Factura(numero, strProveedor, fecha, 
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
                    <label for="fecha" class="">Fecha</label>
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
        <% } %>

    </body>
</html>