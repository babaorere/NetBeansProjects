<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/views/index.jsp">Mi Colegio </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
       
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/views/index.jsp">Home</a>
        </li>
         <li class="nav-item">
          <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/ControladorEstudiante">Ver estudiante</a>
         </li>
         <li class="nav-item">
          <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/ControladorFacilitador">Ver facilitador</a>          
         </li>
          <li class="nav-item">
         <a class="nav-link" aria-current="page" href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rodrigo Riveros - Curso: FullSatck Java  </a> 
         </li> 
      </ul>
    </div>
  </div>
</nav>