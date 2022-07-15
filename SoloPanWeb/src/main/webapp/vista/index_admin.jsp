<!DOCTYPE html>
<html>
    <head>
        <!-- comment -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

        <!-- comment -->
        <link href="../css/estilos.css" rel="stylesheet" type="text/css"/>

        <!-- comment -->
        <script src="https://kit.fontawesome.com/848895ce17.js" crossorigin="anonymous"></script>   

        <title>Solo Pan</title>

    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();

            if (sesion.getAttribute("rol") == null) {
                response.sendRedirect("login.jsp");
            } else if (sesion.getAttribute("rol").equals("C")) {
                response.sendRedirect("index_cliente.jsp");
            } else if (!sesion.getAttribute("rol").equals("A")) {
                response.sendRedirect("error_access.jsp");
            }

        %>

        <header>
            <h1>Solo Pan</h1>
            <h6>y con Vino, mejor</h6>
            <br/>            
        </header>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary mt-1" >
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Inicio</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Pedidos
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                                <li><a class="dropdown-item" href="pedido.jsp">Pedido</a></li>
                                <li><a class="dropdown-item" href="pedido_hist.jsp">Hist&oacute;rico</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Solicitudes
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                                <li><a class="dropdown-item" href="solicitudes.jsp">Solicitudes</a></li>
                                <li><a class="dropdown-item" href="solicitudes_hist.jsp">Hist&oacute;rico</a></li>
                            </ul>
                        </li>                        
                    </ul>
                </div>
            </div>
        </nav>

        <div id="contenedor" class="fotoFondo">
        </div>

        <footer class="navbar-dark bg-primary"> 
            <h6>Copyright Todos los derechos Reservados &copy;2022</h6>
        </footer>
    </body>
</html>
