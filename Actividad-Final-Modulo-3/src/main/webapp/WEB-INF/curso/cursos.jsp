<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <!-- Head -->
    <jsp:include page="/WEB-INF/layout/head.jsp"/>

    <body>
        <!-- Header -->
        <jsp:include page="/WEB-INF/layout/header.jsp"/>

        <!-- NavBar -->
        <jsp:include page="/WEB-INF/layout/navbar.jsp"/>

        <!-- Listado -->
        <jsp:include page="/WEB-INF/curso/listadoCursos.jsp"/>

        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>

    </body>
</html>