<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <!-- Head -->
    <jsp:include page="/WEB-INF/comunes/head.jsp"/>

    <body>
        <!-- Header -->
        <jsp:include page="/WEB-INF/comunes/header.jsp"/>


        <form action="${pageContext.request.contextPath}/ControladorEstudiante?accion=modificar&idEstudiante=${estudiante.idEstudiante}"
              method="POST" class="was-validated">

            <section id="details">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="card">
                                <div class="card-header">
                                    <h4>Editar estudiante ID ${estudiante.idEstudiante}</h4>
                                </div>
                                
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="nombre">Rut</label>
                                        <input type="text" class="form-control" name="rut" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="apellido">Nombre</label>
                                        <input type="text" class="form-control" name="nombre" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" required>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="telefono">Teléfono</label>
                                        <input type="tel" class="form-control" name="telefono" required>
                                    </div>

                                    <div class="row">
                                        <div class="col-3">
                                            <button type="submit" class="btn btn-success btn-block">
                                                <i class="fas fa-check"></i> Guardar Cliente
                                            </button>
                                        </div>
                                        <br>
                                        <div class="col-3">
                                            <a href="${pageContext.request.contextPath}/ControladorEstudiante?accion=eliminar&idEstudiante=${estudiante.idEstudiante}"
                                               class="btn btn-danger btn-block">
                                                <i class="fas fa-trash"></i> Eliminar Cliente
                                            </a>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
                                               
        </form>

</body>
</html>