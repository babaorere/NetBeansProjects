<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <!-- Head -->
    <jsp:include page="/WEB-INF/layout/head.jsp"/>

    <body>
        <!-- Header -->
        <jsp:include page="/WEB-INF/layout/header.jsp"/>


        <form action="${pageContext.request.contextPath}/curso?action=update&id=${curso.id}"
              method="POST" class="was-validated">

            <section id="details">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="card">
                                <div class="card-header">
                                    <h4>Update ID: ${curso.id}</h4>
                                </div>

                                <div class="card-body">

                                    <div class="form-group">
                                        <label for="id">ID</label>
                                        <input type="text" class="form-control" name="id" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="codigo">Código</label>
                                        <input type="text" class="form-control" name="codigo" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="nombre">Nombre</label>
                                        <input type="text" class="form-control" name="nombre" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="idFacilitador">ID Facilitador</label>
                                        <input type="text" class="form-control" name="idFacilitador" required>
                                    </div>

                                    <div class="row">
                                        <div class="col-3">
                                            <button type="submit" class="btn btn-success btn-block">
                                                <i class="fas fa-check"></i> Update
                                            </button>
                                        </div>
                                        <br>
                                        <div class="col-3">
                                            <a href="${pageContext.request.contextPath}/curso?action=delete&id=${curso.idCurso}"
                                               class="btn btn-danger btn-block">
                                                <i class="fas fa-trash"></i> Delete
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