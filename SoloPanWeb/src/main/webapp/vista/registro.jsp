<%@page import="java.sql.PreparedStatement"%>
<%@page import="servicios.Conn"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!doctype html>
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


        <title>Solo Pan - Registro</title>

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
                height: 300px;
                background-color: #8b5742;                    
            }

            .form input {
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


    </head>
    <body oncontextmenu='return false' class='snippet-body'>

        <header>
            <h1>Solo Pan</h1>
            <h6>y con Vino, mejor</h6>
            <br/>            
        </header>

        <div class="d-flex justify-content-center align-items-center mt-5">
            <div class="card">
                <div class="tab-content" id="pills-tabContent">
                    <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
                        <div class="form px-4 pt-5"> 
                            <form method="post" action="#" id="form1">
                                <input type="text" name="reg_nombre" class="form-control" placeholder="Nombre" required> 
                                <input type="email" name="reg_email" class="form-control" placeholder="Email" required> 
                                <input type="password" name="reg_password" class="form-control" placeholder="Password" required> 
                                <button type="button" class="btn btn-dark btn-block" name="log_btn" value="log_btn" onclick="window.location.href = 'login.jsp'">Login</button> 
                                <button type="submit" class="btn btn-dark btn-block" name="reg_btn" value="reg_btn">Registro de nuevo Usuario</button> 
                            </form>
                        </div>
                    </div>       
                </div>
            </div>
        </div>

        <%

            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            if (request.getParameter("reg_btn") != null) {
                String reg_nombre = request.getParameter("reg_nombre").trim().toUpperCase();
                String reg_email = request.getParameter("reg_email").trim();
                String reg_password = request.getParameter("reg_password");
                HttpSession sesion = request.getSession();

                try {
                    con = Conn.getConnection();
                    ps = con.prepareStatement(" SELECT * FROM `usuarios` WHERE nombre='" + reg_nombre + "' OR email='" + reg_email + "'; ");
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Usuario o Email ya registrado </div>");
                    } else {

                        ps = con.prepareStatement("INSERT INTO usuarios(nombre, email, password) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, reg_nombre);
                        ps.setString(2, reg_email);
                        ps.setString(3, reg_password);

                        if (ps.executeUpdate() == 1) {

                            ResultSet generatedKeys = ps.getGeneratedKeys();
                            int id_usuario = 0;
                            if (generatedKeys.next()) {
                                id_usuario = generatedKeys.getInt(1);
                            }

                            sesion.setAttribute("id_usuario", id_usuario);
                            sesion.setAttribute("email", reg_email);
                            sesion.setAttribute("rol", "C");
                            response.sendRedirect("index_cliente.jsp");

                        } else {
                            out.print(" <div class=\"alert alert-danger\" role=\"alert\"> ERROR, no se pudo ingresar al Usuario </div>");
                        }

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
            } else if (request.getParameter("log_btn") != null) {
                response.sendRedirect("login.jsp");
            }

        %>        


        <footer class="navbar-dark bg-primary mt-5" > 
            <h6>Copyright Todos los derechos Reservados &copy;2022</h6>
        </footer>


    </body>
</html>