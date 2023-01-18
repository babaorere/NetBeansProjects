<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding: 25px;" class="container-md">

    <div class="row">
        <div class="col-9">
            <div class="card">
                <table class="table table-striped">
                    <thead class="thead-light">
                        <tr>
                            <th>id</th>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>id Fac.</th>
                            <th>Update</th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${listado}">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.codigo}</td>
                                <td>${item.nombre}</td>
                                <td>${item.idFacilitador}</td>
                                <td>${item.lastUpdate}</td>
                                <td><a href="${pageContext.request.contextPath}/curso?action=edit&id=${item.id}"
                                       class="btn btn-secondary">
                                        <i class="fas fa-angle-double-right"></i> Edit
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
                <jsp:include page="agregar.jsp"/>
            </div>

        </div>
    </div>
</div>


