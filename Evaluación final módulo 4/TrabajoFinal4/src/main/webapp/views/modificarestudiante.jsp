<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <!-- <link href="${pageContext.request.contextPath}/Estilos/main.css" rel="stylesheet" type="text/css"/> -->
        <title>Modificar Estudiante</title>
         <style type="text/css">
			h1{
			text-align:center;	
			font-size:1.1em;
			}
			h2{
  text-align:center;
}

h2 a {
  color: rgb(50,50,50);
  text-decoration: none;
 }

.form{
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
 }

.form input{
  width: 90%;
  height: 30px;
  margin: .5rem
}

.form button{
  padding: .5em 1em;
  border:none;
  background: rgb(100,200,255);
  cursor: pointer;
}

.form-container{
  max-width:600px;
  margin: 0 auto;
}

.form-element {
  display: block;
  width: 60%;
  margin: 1rem auto;
  
}
</style>      
    </head>
 <body> 
<div class="container">
<%@include file="/views/menu.jsp" %>	
</div> 
  
        <h1>Modificar Información del Estudiante</h1>
         <main>
           <div class="form-container" >
             <form action="ControladorEstudiante"  method="get">
                 <label for="id">Identificador</label>
                 <input class="form-element" name="id" type="text" value="${estudiante.getId()}" readonly>
                 <label for="rut">Rut</label>
                 <input class="form-element" name="rut" type="text" value="${estudiante.getRut()}">
                 <label for="nombre">Nombre</label>                 
                 <input class="form-element" name="nombre" type="text" value="${estudiante.getNombre()}">
                 <label for="email">Email</label>  
                 <input class="form-element" name="email" type="text" value="${estudiante.getEmail()}">
                 <label for="telefono">Tel&eacute;fono</label>
                 <input class="form-element" name="telefono" type="text" value="${estudiante.getTelefono()}">
                 <label for="lastUpdate">Last Update</label>
                 <input class="form-element" name="lastUpdate" type="text" value="${estudiante.getLast_update()}" readonly>
                 <input type="hidden" name="op" value="update">
                 <input class="form-element" type="submit"  value="Enviar Modificación">
             </form>
           </div>
        </main>  
 <script type="text/javascript">
  <!--
  document.querySelector('.form').addEventListener('submit',e => {
	  e.preventDefault()
	})
  //-->
  </script>                  
  </body>
</html>
