<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrar Estudiante</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
       
    </head>
    <body>        
        <h1>Registrar Nuevo Estudiante</h1>
      
       <main>
             <form action="${pageContext.request.contextPath}/ControladorEstudiante" method="get">
                 <label for="rut">Rut</label>
                 <input class="FrmDeRegistro" name="rut" type="text">
                 <label for="nombre">Nombre</label>
                 <input name="nombre" type="text">
                 <label for="email">Email</label>
                 <input name="email" type="text">
                 <label for="telefono">Tel&eacute;fono</label>
                 <input name="telefono" type="text">
                 <label for="lastUpdate">Last Update</label>
                 <input name="lastUpdate" type="text" readonly>
        
        <input type="hidden" name="op" value="insertar">
                 <input type="submit"  value="Registrar Estudiante">
             </form>
        </main>
</body>
</html>