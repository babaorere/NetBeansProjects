<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding: 15px;" class="container-md">
    <div class="row">
        <div class="col-11">
            <div class="card">
                <table class="table table-striped">
                    <thead class="thead-light">
                        <tr>
                            <th>#</th>
                            <th>RUT</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Teléfono</th>
                            <th>$</th>
                            <th>Banco</th>
                            <th>Cuenta</th>
                            <th>Update</th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="f" items="${facilitador}">
                        <tr>
                            <td><p class="text-secondary"><c:out value="${f.getIdFacilitador()}"></p></c:out></td>
                            <td><c:out value="${f.getRutFac()}"></c:out></td>
                            <td><c:out value="${f.getNombreFac()}"></c:out></td>
                            <td><c:out value="${f.getEmailFac()}"></c:out></td>
                            <td><c:out value="${f.getTelefonoFac()}"></c:out></td>
                            <td><c:out value="${f.getValorHoraFac()}"></c:out></td>
                            <td><c:out value="${f.getBancoFac()}"></c:out></td>
                            <td><c:out value="${f.getCtaBcoFac()}"></c:out></td>
                            <td><c:out value="${f.getLastUpdateFac()}"></c:out></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>