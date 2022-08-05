<%@page import="java.sql.Statement"%>
<%@page import="servicios.Conn"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
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


        <style>

            .card {
                width: 400px;
                border: none
            }

            .btn-dark {
                color: #fff;
                background-color: #0d6efd;
                border-color: #0d6efd
            }

            .btn-dark:hover {
                color: #fff;
                background-color: #0d6efd;
                border-color: #0d6efd
            }

            .form {
                padding: 10px;
                height: 260px;
                background-color: forestgreen;                    
            }

            .form input {
                margin-top: 5px;
                margin-bottom: 12px;
                border-radius: 3px
            }

            .form input:focus {
                box-shadow: none
            }

            .form button {
                margin-top: 20px
            }



        </style>

        <title>Solo Pan</title>

    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();

            if ((sesion.getAttribute("rol") == null)
                    || (!sesion.getAttribute("rol").equals("A")) && (!sesion.getAttribute("rol").equals("C"))) {
                response.sendRedirect("/SoloPanWeb/vista/login.jsp");
            }
        %>

        <header>
            <h1>Solo Pan</h1>
            <h6>y con Vino, mejor</h6>
            <br/>            
        </header>

        <nav class="navbar navbar-expand-lg navbar-dark bg-primary mt-1" >
            <div class="container-fluid">
                <a class="navbar-brand" href="../index.jsp">Inicio</a>
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
            <div class="d-flex justify-content-center align-items-center mt-1">
                <div class="card">
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
                            <div class="form px-4 pt-5"> 
                                <form method="post" action="#" id="form_1">
                                    <label class="mb-2"><h3>Realice su pedido</h3></label><br>
                                    <select name= "pedido_tipo" class="form-select" aria-label="Default select example" required>
                                        <option value="" selected>Seleccione el Tipo de Pan</option>
                                        <option value="Amasado">Amasado</option>
                                        <option value="Marraquetas">Marraquetas</option>
                                        <option value="Hallullas">Hallullas</option>
                                    </select>                                    
                                    <input type="number" name="pedido_cant" class="form-control" placeholder="Cantidad de Pan" required> 
                                    <button type="submit" class="btn btn-dark btn-block" name="pedido_btn" value="pedido_btn">Enviar</button> 
                                </form>
                            </div>
                        </div>       
                    </div>
                </div>
            </div>            
        </div>

        <%
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            if (request.getParameter("pedido_btn") != null) {
                String pedido_tipo = request.getParameter("pedido_tipo").trim().toUpperCase();
                String pedido_cant = request.getParameter("pedido_cant").trim();
                int id_usuario = (int) sesion.getAttribute("id_usuario");

                try {
                    con = Conn.getConnection();
                    ps = con.prepareStatement("INSERT INTO pedidos(tipo_pan, cant_pan, id_usuario) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, pedido_tipo);
                    ps.setString(2, pedido_cant);
                    ps.setInt(3, id_usuario);

                    if (ps.executeUpdate() == 1) {
                        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Pedido realizado </div>");

                        ResultSet generatedKeys = ps.getGeneratedKeys();
                        int id_pedido = 0;
                        if (generatedKeys.next()) {
                            id_pedido = generatedKeys.getInt(1);
                        }

                    } else {
                        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> ERROR, no se pudo ingresar el Pedido </div>");
                    }

                } catch (Exception ex) {
                    out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Error en la Base de Datos<br> " + ex + " </div>");
                } finally {
                    try {

                        if (ps != null) {
                            ps.close();
                        }

                        if (con != null) {
                            Conn.CloseConnection();
                        }

                    } catch (Exception ex) {
                        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Error en la Base de Datos<br> " + ex + " </div>");
                    }
                }
            }

        %>        


        <footer class="navbar-dark bg-primary"> 
            <h6>Copyright Todos los derechos Reservados &copy;2022</h6>
        </footer>
    </body>
</html>
