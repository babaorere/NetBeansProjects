
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

<%
    HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null)
    {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Principal</title>
        
       <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>    
               
        
    </head>
    <body>
       
        <nav class="navbar navbar-expand-lg navbar-light bg-dark">
          <nav class="navbar navbar-dark bg-dark">  
              <a class="navbar-brand" href="#"><img src="imagen/userIcon.jpg" alt="" width="30" height="30"/></a>
         
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="login.jsp">Home <span class="sr-only">(current)</span></a>
      </li>    
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Proveedor
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Nuevo</a>
          <a class="dropdown-item" href="#">Modificar</a>
          <a class="dropdown-item" href="#">Eliminar</a>
          <a class="dropdown-item" href="#">Busqueda</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Mantenimiento a un Proveedor</a>
        </div>
      </li>
           
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Reportes
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Proveedor</a>         
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Visualizacion de reportes</a>
        </div>
      </li>
      
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
       <a href="login.jsp?cerrar=true" button type="button" class="btn btn-outline-primary" >Close</a>
    </form>
  </div>
          </nav>
</nav>
        
    </body>
</html>
