<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="com.webapp.lab_nro3.Cliente" %>

<%  
    int numero;
    try {
        numero= Integer.parseInt(request.getParameter("numero"));
    }  catch(Exception e) { 
        numero= 0;
    }                
                
    Cliente cliente= Cliente.get(numero);  

    boolean marca= "true".equals(((String)request.getParameter("marca")));

%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Modificar Cliente</title>

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
        <h1 class="mycenter">Modificar Cliente</h1>
        <br>

        <% if(cliente == null) { %>
        <%= numero %>
        <p class="mycenter" style="color:red;">Cliente no encontrado</p>
        <br>
        <ul><li><a href="index.jsp">Home</a></li></ul>

        <% } else { %>
        <div class='container py-5 px-5'>

            <%
                String strNumero;
                String strNombre;
                String strApellido;                
                String strDireccion;                
                String strTelefono;                
                String strActividad;

                if(marca) {
                    strNumero= (String)request.getParameter("numero");
                    if(strNumero == null) {
                        strNumero= "";
                    }
                
                    strNombre= (String)request.getParameter("nombre");
                    if(strNombre == null) {
                        strNombre= "";
                    }
                    
                    strApellido= (String)request.getParameter("apellido");
                    if(strApellido == null) {
                        strApellido= "";
                    }
                
                    strDireccion= (String)request.getParameter("direccion");
                    if(strDireccion == null) {
                        strDireccion= "";
                    }
                
                    strTelefono= (String)request.getParameter("telefono");
                    if(strTelefono == null) {
                        strTelefono= "";
                    }
                
                    strActividad= (String)request.getParameter("actividad");
                    if(strActividad == null) {
                        strActividad= "";
                    }
                                
                } else {
                
                    strNumero= String.valueOf(cliente.getNumero());
                    if(strNumero == null) {
                        strNumero= "";
                    }
                
                    strNombre= cliente.getNombre();
                    if(strNombre == null) {
                        strNombre= "";
                    }
                
                    strApellido= cliente.getApellido();
                    if(strApellido == null) {
                        strApellido= "";
                    }
                
                    strDireccion= cliente.getDireccion();
                    if(strDireccion == null) {
                        strDireccion= "";
                    }
                
                    strTelefono= cliente.getTelefono();
                    if(strTelefono == null) {
                        strTelefono= "";
                    }
                
                    strActividad= cliente.getActividad();
                    if(strActividad == null) {
                        strActividad= "";
                    }
                
                }

                boolean valNumero= strNumero.length() != 0;
                
                try {
                    if(valNumero) {
                        numero= Integer.parseInt(strNumero);
                    }
                }  catch(Exception e) { 
                   valNumero= false;
                }                
                
                boolean valNombre= strNombre.length() != 0;
                boolean valApellido= strApellido.length() != 0;
                boolean valDireccion= strDireccion.length() != 0;
                boolean valTelefono= strTelefono.length() != 0;
                boolean valActividad= strActividad.length() != 0;
                
                boolean datosValidos= valNumero && valNombre && valApellido
                        && valDireccion && valTelefono && valActividad;
            
             if(marca && datosValidos) {                                 
                if (Cliente.update(new Cliente(numero, strNombre, strApellido, 
                    strDireccion, strTelefono, strActividad)) == 1) { 
                    response.sendRedirect("cliente_tabla.jsp");              
                } else { %>
            <p style="color:red;">Error al tratar de guardar los datos</p>
            <ul><li><a href="index.jsp">Home</a></li></ul>
                    <% } %>
            <br>
            <% } else { %>

            <form action="#" method="post">
                <br>
                <div class="">
                    <label for="numero" class="">Número</label>
                    <input type="text" class="" id="numero" name="numero" placeholder="Número" readonly
                           value=<%= strNumero %>>
                    <% if(marca && !valNumero) { %>
                    <p style="color:red;">El número no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="nombre" class="">Nombre</label>
                    <input type="text" class="" id="nombre" name="nombre" placeholder="Nombre"
                           value=<%= strNombre %>>
                    <% if(marca && !valNombre) { %>
                    <p style="color:red;">el nombre no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="apellido" class="">Apellido</label>
                    <input type="text" class="" id="apellido" name="apellido" placeholder="Apellido"
                           value=<%= strApellido %>>
                    <% if(marca && !valApellido) { %>
                    <p style="color:red;">el apellido no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="direccion" class="">Dirección</label>
                    <input type="text" class="" id="direccion" name="direccion" placeholder="Dirección"
                           value=<%= strDireccion %>>
                    <% if(marca && !valDireccion) { %>
                    <p style="color:red;">La dirección no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="telefono" class="">Teléfono</label>
                    <input type="text" class="" id="telefono" name="telefono" placeholder="Teléfono"
                           value=<%= strTelefono %>>
                    <% if(marca && !valTelefono) { %>
                    <p style="color:red;">El teléfono no puede estar vacío</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="actividad" class="">Actividad</label>
                    <input type="text" class="" id="actividad" name="actividad" placeholder="Actividad"
                           value=<%= strActividad %>>
                    <% if(marca && !valActividad) { %>
                    <p style="color:red;">La actividad no puede estar vacía</p>
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