<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="com.webapp.lab_nro3.Cliente" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Cliente</title>
    </head>

    <body>
        <div class='container py-5 px-5'>

            <h1 style="text-align:center">Cliente</h1>
            <br>
            <%
                boolean marca= "true".equals(((String)request.getParameter("marca")));

                String strNumero= (String)request.getParameter("numero");
                if(strNumero == null) {
                    strNumero= "0";
                }
                
                String strNombre= (String)request.getParameter("nombre");
                if(strNombre == null) {
                    strNombre= "";
                }
                
                String strApellido= (String)request.getParameter("apellido");
                if(strApellido == null) {
                    strApellido= "";
                }
                
                String strDireccion= (String)request.getParameter("direccion");
                if(strDireccion == null) {
                    strDireccion= "";
                }
                
                String strTelefono= (String)request.getParameter("telefono");
                if(strTelefono == null) {
                    strTelefono= "";
                }
                
                String strActividad= (String)request.getParameter("actividad");
                if(strActividad == null) {
                    strActividad= "";
                }

                int numero= 0;
                boolean valNumero= true;
                boolean valNombre= strNombre.length() != 0;
                boolean valApellido= strApellido.length() != 0;
                boolean valDireccion= strDireccion.length() != 0;
                boolean valTelefono= strTelefono.length() != 0;
                boolean valActividad= strActividad.length() != 0;
                
                boolean datosValidos= valNumero && valNombre && valApellido
                        && valDireccion && valTelefono && valActividad;
            
             if(marca && datosValidos) {                                 
                if (Cliente.save(new Cliente(numero, strNombre, strApellido, 
                    strDireccion, strTelefono, strActividad)) == 1) {
                    response.sendRedirect("cliente_tabla.jsp");              
                } else { %>
            <p style="color:red;">Error al tratar de guardar los datos</p>
            <br>
            <ul><li><a href="index.jsp">Home</a></li></ul>
                    <% } %>
                    <% } else { %>

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
    </body>
</html>