<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%
 Map<String, String> errores = (Map<String, String>)request.getAttribute("errores");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />       

        <title>Test Input Form Controls</title>

        <!-- Favicon-->
        <!-- Referencia al favicon, es decir aquel que aparecerea el la pestaña de titulo -->
        <link rel="icon" type="image/x-icon" href="images/jakarta_favicon.png" />        

        <!-- Referencia a las distintas hojas de estilos CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- DANGER, NO, NO, NO, establecer <<media=”screen”>> para la hoja de estilos, puede traer problemas -->
        <link href="css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Referencia al JS para los iconos, por sugerencia de fontawesome.com, debe estar en esta seccion -->
        <script src="https://kit.fontawesome.com/848895ce17.js" crossorigin="anonymous"></script>        

    </head>

    <body>

        <!-- Incluir el layout correspondiente al navbar, el cual es identico, y se repite en todo el website -->
        <jsp:include page="views/layouts/navbar.jsp"/>

        <div class="container-fluid" style="margin-top:60px">
            <header>
                <h1 class="center">Pruebas de distintos tipos de HTML Input</h1>
            </header>
            <br/>

            <p class="mycenter_txt">
                La intensión de este website, es la demostración de varios aspectos en la construcción de paginas Web con Java: <br/>
                .- Utilización de HTML y JSP<br/>
                .- Utilización básica de Bootstrap, navbar, form-controls, etc<br/>
                .- Utilización de link, imágenes, y similares<br/>
                .- Utilización de variables de Sesión, de petición(request), tipo "globales", pase de parámetros<br/>
                .- Utilización de Filtros<br/>                                
            </p>

            <br/>
            <br/>
        </div>


        <!-- Incluir el layout correspondiente al footer, el cual es identico, y se repite en todo el website -->
        <jsp:include page="views/layouts/footer.jsp"/>

        <!-- Script Section -->          

        <!-- Bootstrap -->          
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>                

    </body>


</html>