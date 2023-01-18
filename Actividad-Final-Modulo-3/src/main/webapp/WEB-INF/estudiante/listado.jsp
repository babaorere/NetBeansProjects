<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding: 25px;" class="container-md">

    <div class="row">
        <div class="col-9">
            <div class="card">
                <table class="table table-striped">
                    <thead class="thead-light">
                        <tr>
                            <th>#</th>
                            <th>RUT</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Teléfono</th>
                            <th>Update</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="estudiantes" items="${estudiantes}">
                            <tr>
                                <td>${estudiantes.idEstudiante}</td>
                                <td>${estudiantes.rutEst}</td>
                                <td>${estudiantes.nombreEst}</td>
                                <td>${estudiantes.emailEst}</td>
                                <td>${estudiantes.telefonoEst}</td>
                                <td>${estudiantes.lastUpdateEst}</td>
                                <td><a href="${pageContext.request.contextPath}/ControladorEstudiante?accion=editar&idEstudiante=${estudiantes.idEstudiante}"
                                       class="btn btn-secondary">
                                        <i class="fas fa-angle-double-right"></i> Editar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-3">

            <div>
                <jsp:include page="estudianteAgregar.jsp"/>
            </div>

            <div class="card text-center bg-success text-white mb-3">
                <div class="card-body">
                    <h3>Registros totales </h3>
                    <h5 class="display-4">
                        <i class="fas fa-users"></i> ${totalEstudiantes}
                    </h5>
                </div>
            </div>
                    
            

        </div>
    </div>
</div>




