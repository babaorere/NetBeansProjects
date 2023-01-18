<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<style type="text/css">
table td{
border-style: solid;
}	
th {
  background-color: green;
  opacity: 0.7;
  color:black;
  }
h1{
   text-align:center;	
   font-size:1.1em;
	}
.con{
  width: 100%;
  max-width: 1300px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
 }
</style>
</head>
<body>
<div class="container">
<%@include file="/views/menu.jsp" %>	
</div>
<div class="con" >
  <h1>Listado de Facilitadores</h1>
  <table>
  <thead>
  <th>Id</th>
  <th>Rut</th>
  <th>Nombre</th>
  <th>Email</th>
  <th>Telefono</th>  
  <th>Valor hora</th>  
  <th>Banco</th>  
  <th>Cuenta bancaria</th>  
  <th>Last Update</th>  
  <th>Editar</th> 
  <th>Borrar</th>  
  </thead> 
  <tbody>
  <c:forEach var = "f" items = "${Facilitadores}" >
  <tr>
  <td>${f.getId()}</td>
  <td>${f.getRut()}</td>
  <td>${f.getNombre()}</td>
  <td>${f.getEmail()}</td>
  <td>${f.getTelefono()}</td>
  <td>${f.getValorhora()}</td>
  <td>${f.getBanco()}</td>
  <td>${f.getCtabancaria()}</td>
  <td>${f.getLast_update()}</td>
  <td> 
      <a href="${pageContext.request.contextPath}/ControladorFacilitador?op=actualizar&id=${f.getId()}" >Editar</a>
  </td> 
  <td> 
      <a href="${pageContext.request.contextPath}/ControladorFacilitador?op=eliminar&id=${f.getId()}" >Borrar</a>
  </td> 
  </tr>
  
  </c:forEach>
    </tbody>  
  </table>
 </div>
</body>
</html>
  