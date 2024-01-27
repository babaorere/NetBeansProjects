
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/sweetalert.min.js" type="text/javascript"></script>
        
        
    </head>
    <body>
        
        <form name="frmUser" action="srvUser" method="POST">
            
             <div class="container">
            
            <div class="row d-flex justify-content-center"> 
                <div class="col-md-5">
  <table class="table table-dark">
  <thead>
    <tr>
  <div align="center">
        <img src="imagen/user.png" alt="" with="200" height="200"/>
  </div>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>usuario</td>
      <td><input type="text" name="txtUsuario" value="" /></td>
    </tr>
    <tr>      
      <td>Contraseña</td>
      <td><input type="password" name="txtPassword" value="" /></td>
    </tr>
    <tr>     
        <td colspan="2" align="center"><input type="submit" value="INGRESAR" name="btnIngresar"  class="btn btn-primary" /></td>
    </tr>
  </tbody>
</table>
                    </div>
                </div>
        
      </div>   
            
        </form>
        
<% 
    HttpSession sesion = request.getSession();
    
    if (request.getAttribute("usuario")!=null)
    {
        if (request.getAttribute("respuesta")=="True")
            {
                sesion.setAttribute("usuario", request.getAttribute("usuario"));
                response.sendRedirect("menu.jsp");
            }
        else
            {
              %>
    <script> swal("Acceso incorrecto!", "Usuario y contraseña incorrecta", "error");</script>
    
    <%
            }
    }

if (request.getParameter("cerrar")!= null)
{
    session.invalidate();
}

%>
       
        
    </body>
    
</html>
