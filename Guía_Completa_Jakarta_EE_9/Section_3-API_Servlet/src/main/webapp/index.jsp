<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />       

        <title>Section_3-API_Servlet</title>

        <!-- Favicon-->
        <!-- Referencia al favicon, es decir aquel que aparece en la pestaña de titulo -->
        <link rel="icon" type="image/x-icon" href="views/images/jakarta_favicon.png" />        

        <!-- Referencia a las distintas hojas de estilos CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <!-- DANGER, NO, NO, NO, establecer <<media=”screen”>> para la hoja de estilos, puede traer problemas -->
        <link href="views/css/style.css" rel="stylesheet" type="text/css"/>

        <!-- Referencia al JS para los iconos, por sugerencia de fontawesome.com, debe estar en esta seccion -->
        <script src="https://kit.fontawesome.com/848895ce17.js" crossorigin="anonymous"></script>        

    </head>

    <body>

        <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top px-2">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}">
                    <img src="views/images/code-solid_32x32.png" class="d-inline-block align-top" alt="logo">
                    Home
                </a>        


                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link active" href="${pageContext.request.contextPath}/run">
                            Servlet
                        </a>

                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}">
                            MenuItem_1
                        </a>

                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}">
                            MenuItem_2
                        </a>
                    </div>
                </div>
            </div>
        </nav>
        <div class="container-fluid" style="margin-top:60px">
            <header>
                <h1 class="center">Section_3-API_Servlet</h1>
            </header>
            <br/>

            <div class="mycenter_txt">
                <p>
                    El objetivo de esta sección es la demostración del uso básico de un Servlet.
                    <br>
                    https://www.udemy.com/course/curso-jakartaee-java-ee-9-desde-cero-a-experto/
                </p>

                <div class="mycenter_img">
                    <img src="views/images/jakarta_favicon.png" alt=""/>
                </div>
            </div>
        </div>

        <br/>
        <br/>

        <!-- Footer-->
        <footer class="footer text-center">
            <div class="container">
                <div class="row">

                    <!-- Footer Location-->
                    <div class="col-lg-4 mb-5 mb-lg-0">
                        <h4 class="text-uppercase mb-4">Localidad</h4>
                        <p class="lead mb-0">
                            América / Chile
                            <br />
                            RM Santiago
                            <br />
                            Comuna de Puente Alto
                        </p>
                    </div>

                    <!-- Footer Social Icons-->
                    <div class="col-lg-4 mb-5 mb-lg-0">
                        <h4 class="text-uppercase mb-4">Alrededor de la Web</h4>
                        <a class="btn btn-outline-light btn-social mx-1" href="#!"><i class="fab fa-fw fa-facebook-f"></i></a>
                        <a class="btn btn-outline-light btn-social mx-1" href="#!"><i class="fab fa-fw fa-twitter"></i></a>
                        <a class="btn btn-outline-light btn-social mx-1" href="#!"><i class="fab fa-fw fa-linkedin-in"></i></a>
                    </div>

                    <!-- Footer About Text-->
                    <div class="col-lg-4">
                        <h4 class="text-uppercase mb-4">Acerca del Freelancer</h4>
                        <p class="lead mb-0">
                            Este Website es de uso libre,

                            <a href="mailto:roger.gallegos.cl@gmail.com?Subject=Más%20información%20sobre%20la%20página">
                                Contacto
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </footer>

        <!-- Copyright Section-->
        <div class="copyright py-4 text-center text-white">
            <div class="container"><small>Roger Gallegos, Marzo 2022</small></div>
            <!--<div class="container"><small>Copyright &copy; 2022</small></div>-->
        </div>

        <!-- Script Section -->          

        <!-- Bootstrap -->          
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>                

    </body>

</html>