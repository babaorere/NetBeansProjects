<%-- 
    Document   : navbar
    Created on : 1 mar. 2022, 12:11:09 p. m.
    Author     : manager
--%>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top px-2">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">
            <img src="../images/code-solid_32x32.png" class="d-inline-block align-top" alt="logo">
            Home
            <img src="views/images/code-solid_32x32.png" alt=""/>
        </a>        


        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" 
                   href=${pageContext.request.contextPath}>Home <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="simple_inputs.jsp">Simple-Inputs</a>
                <a class="nav-item nav-link" href="group_inputs.jsp">Group-Inputs</a>
            </div>
        </div>
    </div>
</nav>
