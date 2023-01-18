<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding: 25px;" class="container-md">

    <div class="row">
        <div class="col-11">
            <div class="card">
                <table class="table table-striped">
                    <thead class="thead-light">
                        <tr>
                            <th># Estudiante</th>
                            <th># Curso</th>
                            <th>Fecha Matrícula</th>
                            <th>Fecha Update</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="matriculas" items="${matriculas}">
                            <tr>
                                <td>${matriculas.idEstudianteFK}</td>
                                <td>${matriculas.idCursoFK}</td>
                                <td>${matriculas.fecha}</td>
                                <td>${matriculas.lastUpdateMatricula}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</div>


