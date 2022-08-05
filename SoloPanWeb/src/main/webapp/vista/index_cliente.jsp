<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"/>
        <script src="https://kit.fontawesome.com/848895ce17.js" crossorigin="anonymous"></script>   

        <link href="../css/estilos.css" rel="stylesheet" type="text/css"/>

        <title>Solo Pan</title>

    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();

            if (sesion.getAttribute("rol") == null) {
                response.sendRedirect("login.jsp");
            } else if (sesion.getAttribute("rol").equals("A")) {
                response.sendRedirect("index_admin.jsp");
            } else if (!sesion.getAttribute("rol").equals("C")) {
                response.sendRedirect("error_access.jsp");
            }

        %>

        <header>
            <h1>Solo Pan</h1>
            <h6>y con Vino, mejor</h6>
            <br/>            
        </header>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary mt-1" >
            <a class="navbar-brand" href="#">Home</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link active" href="vista/pedido.jsp">Pedido<span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link active" href="vista/historico.jsp">Histórico</a>
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
