<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar Estudiante</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
       
    </head>
    <body>        
        <h1>Registrar Nuevo Facilitador</h1>
      
       <main>
             <form action="${pageContext.request.contextPath}/ControladorFacilitador" method="get">
                 <label for="rut">Rut</label>
                 <input name="rut" type="text">
                 <label for="nombre">Nombre</label>
                 <input name="nombre" type="text">
                 
                 <label for="email">Email</label>
                 <input name="email" type="text">
                 
                 <label for="telefono">Tel&eacute;fono</label>
                 <input name="telefono" type="text">
                 
                 <label for="valorhora">Valor hora</label>
                 <input name="valorhora" type="text">
                 
                 
                 <label for="banco">Banco</label>
                 <input name="banco" type="text">
                 
                  <label for="ctabancaria">Cuenta bancaria</label>
                 <input name="ctabancaria" type="text">
                 
                 <label for="lastUpdate">Last Update</label>
                 <input class="FrmDeRegistro" name="lastUpdate" type="text" readonly>
        
                 <input type="hidden" name="op" value="insertar">
                 <input type="submit"  value="Registrar Estudiante">
             </form>
        </main>
</body>
</html>  