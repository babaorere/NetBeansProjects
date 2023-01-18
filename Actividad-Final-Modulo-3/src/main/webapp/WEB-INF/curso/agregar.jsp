<section id="actions" class="py-1 mb-1 bg-light">
    <div class="container">
        <div class="row">
            <a href="#" class="btn btn-primary btn-block"
               data-toggle="modal" data-target="#agregarCursoModal">
                <i class="fas fa-plus"></i> Create
            </a>
        </div>
    </div>
</section>

<div class="modal fade" id="agregarCursoModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Create</h5> 
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/curso?action=create"
                  method="POST" class="was-validated">

                <div class="modal-body">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="apellido">Código</label>
                            <input type="text" class="form-control" name="codigo" required>
                        </div>
                        <div class="form-group">
                            <label for="text">Nombre</label>
                            <input type="text" class="form-control" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="text">ID Facilitador</label>
                            <input type="text" class="form-control" name="idFacilitador" required>
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