<section id="actions" class="py-1 mb-1 bg-light">
    <div class="container">
        <div class="row">
            <a href="#" class="btn btn-primary btn-block"
               data-toggle="modal" data-target="#agregarEstudianteModal">
                <i class="fas fa-plus"></i> Agregar Estudiante
            </a>
        </div>
    </div>
</section>

<div class="modal fade" id="agregarEstudianteModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Agregar un nuevo estudiante</h5> 
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/ControladorEstudiante?accion=insertar"
                  method="POST" class="was-validated">

                <div class="modal-body">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nombre">RUT</label>
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
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="submit">Guardar</button>
                    </div>    
                </div>    
            </form>
        </div>
    </div>
</div>