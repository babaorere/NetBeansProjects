<!DOCTYPE html>
<html>
    <head>

        <!-- comment -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

        <!-- comment -->
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>

        <!-- comment -->
        <script src="https://kit.fontawesome.com/848895ce17.js" crossorigin="anonymous"></script>   

        <title>Solo Pan</title>

    </head>

    <body>
        <%
            HttpSession sesion = request.getSession();

            // sesion.setAttribute("rol", "A");
            // sesion.setAttribute("id", "1");
            // sesion.setAttribute("email", "roger@gmail.com");
            if (sesion.getAttribute("rol") == null) {
                response.sendRedirect("vista/login.jsp");
            } else {
                if (sesion.getAttribute("rol").equals("A")) {
                    response.sendRedirect("vista/index_admin.jsp");
                } else if (sesion.getAttribute("logueado").equals("C")) {
                    response.sendRedirect("vista/index_cliente.jsp");
                } else if (sesion.getAttribute("logueado").equals("C")) {
                    response.sendRedirect("vista/error_login_registro.jsp");
                }

            }

        %>

    </body>
</html>
