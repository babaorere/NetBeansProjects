<%

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    out.print(request.getParameter("log_email"));
    out.print("<br/>" + request.getParameter("log_email"));
    out.print("<br/>" + request.getParameter("log_btn"));
    out.print("<br/>" + request.getParameter("reg_btn"));

    if (request.getParameter("log_btn") != null) {

        out.print(" <div class=\"alert alert-danger\" role=\"alert\"> LOG BTN </div>");

        String log_email = request.getParameter("log_email");
        String log_password = request.getParameter("log_password");
        HttpSession sesion = request.getSession();

        try {
            con = Conn.getConnection();
            ps = con.prepareStatement(" SELECT * FROM `usuarios` WHERE email='" + log_email + "' AND password='" + log_password + "'; ");
            rs = ps.executeQuery();
            if (rs.next()) {
                String strId = rs.getString("id");
                String email = rs.getString("email");
                int rol = rs.getInt("rol");
                String strRol;

                if (rol == 1) {
                    strRol = "A"; // Administrador
                } else if (rol > 1) {
                    strRol = "C"; // Cliente
                } else {
                    strRol = "E"; // Error
                }

                sesion.setAttribute("logueado", strRol);
                sesion.setAttribute("id", strId);
                sesion.setAttribute("email", email);

                switch (strRol) {

                    case "A":
                        response.sendRedirect("index_admin.jsp");
                        break;

                    case "C":
                        response.sendRedirect("index_cliente.jsp");
                        break;

                    default:
                        response.sendRedirect("log_error.jsp");
                        break;
                }
            }
            out.print(" <div class=\"alert alert-danger\" role=\"alert\"> Usuario o Password valido </div>");
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
        String reg_nombre = request.getParameter("reg_nombre");
        String reg_email = request.getParameter("reg_email");
        String reg_password = request.getParameter("reg_password");
        HttpSession sesion = request.getSession();

        try {
            con = Conn.getConnection();
            ps = con.prepareStatement(" SELECT * FROM `usuarios` where nombre='" + reg_nombre + "' OR email='" + reg_email + "'; ");
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
                    int newId = 0;
                    if (generatedKeys.next()) {
                        newId = generatedKeys.getInt(1);
                    }

                    sesion.setAttribute("logueado", "C");
                    sesion.setAttribute("user", rs.getString("user"));
                    sesion.setAttribute("id", rs.getString("id"));
                    response.sendRedirect("index_cliente.jsp");

                } else {
                    out.print(" <div class=\"alert alert-danger\" role=\"alert\"> ERROR, no se pudo ingresar al Usuario </div>");
                }

            }
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
    }

%>        

<div class="form">

    <ul class="tab-group">
        <li class="tab active"><a href="#signup">Sign Up</a></li>
        <li class="tab"><a href="#login">Log In</a></li>
    </ul>

    <div class="tab-content">
        <div id="signup">   
            <h1>Sign Up for Free</h1>

            <form action="/" method="post">

                <div class="top-row">
                    <div class="field-wrap">
                        <label>
                            First Name<span class="req">*</span>
                        </label>
                        <input type="text" required autocomplete="off" />
                    </div>

                    <div class="field-wrap">
                        <label>
                            Last Name<span class="req">*</span>
                        </label>
                        <input type="text"required autocomplete="off"/>
                    </div>
                </div>

                <div class="field-wrap">
                    <label>
                        Email Address<span class="req">*</span>
                    </label>
                    <input type="email"required autocomplete="off"/>
                </div>

                <div class="field-wrap">
                    <label>
                        Set A Password<span class="req">*</span>
                    </label>
                    <input type="password"required autocomplete="off"/>
                </div>

                <button type="submit" class="button button-block"/>Get Started</button>

            </form>

        </div>

        <div id="login">   
            <h1>Welcome Back!</h1>

            <form action="/" method="post">

                <div class="field-wrap">
                    <label>
                        Email Address<span class="req">*</span>
                    </label>
                    <input type="email"required autocomplete="off"/>
                </div>

                <div class="field-wrap">
                    <label>
                        Password<span class="req">*</span>
                    </label>
                    <input type="password"required autocomplete="off"/>
                </div>

                <p class="forgot"><a href="#">Forgot Password?</a></p>

                <button class="button button-block"/>Log In</button>

            </form>

        </div>

    </div><!-- tab-content -->

</div> <!-- /form -->