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


        <title>Solo Pan - Login</title>

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
                height: 250px;
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
                            <form method="post" action="#" id="form_1">
                                <input type="email" name="log_email" class="form-control" placeholder="Email" required> 
                                <input type="password" name="log_password" class="form-control" placeholder="Password" required> 
                                <button type="submit" class="btn btn-dark btn-block" name="log_btn" value="log_btn">Login</button> 
                                <button type="button" class="btn btn-dark btn-block" name="reg_btn" value="reg_btn" onclick="window.location.href = 'registro.jsp'">Registro de nuevo Usuario</button> 
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

            if (request.getParameter("log_btn") != null) {

                String log_email = request.getParameter("log_email").trim();
                String log_password = request.getParameter("log_password");
                HttpSession sesion = request.getSession();

                try {
                    con = Conn.getConnection();
                    ps = con.prepareStatement(" SELECT * FROM `usuarios` WHERE email='" + log_email + "' AND password='" + log_password + "'; ");
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int id_usuario = rs.getInt("id_usuario");
                        int rol = rs.getInt("rol");
                        String strRol;

                        if (rol == 1) {
                            strRol = "A"; // Administrador
                        } else if ((rol > 1) && (rol < 100)) {
                            strRol = "C"; // Cliente
                        } else {
                            strRol = "E"; // Error
                        }

                        sesion.setAttribute("id_usuario", id_usuario);
                        sesion.setAttribute("email", log_email);
                        sesion.setAttribute("rol", strRol);

                        switch (strRol) {

                            case "A":
                                response.sendRedirect("index_admin.jsp");
                                break;

                            case "C":
                                response.sendRedirect("index_cliente.jsp");
                                break;

                            default:
                                response.sendRedirect("error_login_registro.jsp");
                                break;
                        }
                    }
                    out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Usuario o Password invalido </div>");
                } catch (Exception e) {
                    out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Error en la Base de Datos </div>");
                } finally {
                    try {

                        if (ps != null) {
                            ps.close();
                        }

                        if (con != null) {
                            Conn.CloseConnection();
                        }

                    } catch (Exception e) {
                        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Error en la Base de Datos </div>");
                    }
                }
            } else if (request.getParameter("reg_btn") != null) {
                response.sendRedirect("registro.jsp");
            }

        %>        


        <footer class="navbar-dark bg-primary mt-5" > 
            <h6>Copyright Todos los derechos Reservados &copy;2022</h6>
        </footer>


    </body>
</html>