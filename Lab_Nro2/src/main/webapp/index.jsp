<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Encuesta</title>
    </head>

    <body>
        <div class='container py-5 px-5'>

            <h1 style="text-align:center">Formulario de Encuesta</h1>
            <br>
            <%
                boolean marca= "true".equals(((String)request.getParameter("marca")));
                String pregunta1= (String)request.getParameter("pregunta1");
                String pregunta2= (String)request.getParameter("pregunta2");
                String pregunta3= (String)request.getParameter("pregunta3");
                String pregunta4= (String)request.getParameter("pregunta4");
                String pregunta5= (String)request.getParameter("pregunta5");
                String pregunta6= (String)request.getParameter("pregunta6");
                String pregunta7= (String)request.getParameter("pregunta7");
                String pregunta8= (String)request.getParameter("pregunta8");
                String pregunta9= (String)request.getParameter("pregunta9");
                String pregunta10= (String)request.getParameter("pregunta10");

                boolean datosValidos= marca && (
                        ((pregunta1 != null) && (pregunta1.length() != 0))
                        && ((pregunta2 != null) && (pregunta2.length() != 0))
                        && ((pregunta3 != null) && (pregunta3.length() != 0))
                        && ((pregunta4 != null) && (pregunta4.length() != 0))
                        && ((pregunta5 != null) && (pregunta5.length() != 0))
                        && ((pregunta6 != null) && (pregunta6.length() != 0))
                        && ((pregunta7 != null) && (pregunta7.length() != 0))
                        && ((pregunta8 != null) && (pregunta8.length() != 0))
                        && ((pregunta9 != null) && (pregunta9.length() != 0))
                        && ((pregunta10 != null) && (pregunta10.length() != 0)));
            %>


            <% if(datosValidos) { 
                try {    
                    String filePath = "encuesta.txt";
                    FileWriter fw = new FileWriter(filePath, true); 
                    BufferedWriter bw = new BufferedWriter(fw);                   
                    bw.write(pregunta1 + "\t");
                    bw.write(pregunta2 + "\t");
                    bw.write(pregunta3 + "\t");
                    bw.write(pregunta4 + "\t");
                    bw.write(pregunta5 + "\t");
                    bw.write(pregunta6 + "\t");
                    bw.write(pregunta7 + "\t");
                    bw.write(pregunta8 + "\t");
                    bw.write(pregunta9 + "\t");
                    bw.write(pregunta10 + "\t\n");
                    bw.close(); %>

            <p style="color:blue;">Datos guardados con éxito</p>

            <% } 
            catch(Exception e)
            { %>

            <p style="color:red;">Error al tratar de guardar los datos</p>

            <% }

            } else { %>

            <form action="#" method="post">
                <br>
                <div class="">
                    <label for="pregunta1" class="">Pregunta Nro1</label>
                    <input type="text" class="" id="pregunta1" name="pregunta1" placeholder="Pregunta Nro 1"
                           value=${param.pregunta1}>
                    <% if(marca && ((pregunta1 == null) || (pregunta1.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 1, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta2" class="">Pregunta Nro2</label>
                    <input type="text" class="" id="pregunta2" name="pregunta2" placeholder="Pregunta Nro 2"
                           value=${param.pregunta2}>
                    <% if(marca && ((pregunta2 == null) || (pregunta2.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 2, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta3" class="">Pregunta Nro3</label>
                    <input type="text" class="" id="pregunta3" name="pregunta3" placeholder="Pregunta Nro 3"
                           value=${param.pregunta3}>
                    <% if(marca && ((pregunta3 == null) || (pregunta3.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 3, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta4" class="">Pregunta Nro4</label>
                    <input type="text" class="" id="pregunta4" name="pregunta4" placeholder="Pregunta Nro 4"
                           value=${param.pregunta4}>
                    <% if(marca && ((pregunta4 == null) || (pregunta4.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 4, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta5" class="">Pregunta Nro5</label>
                    <input type="text" class="" id="pregunta5" name="pregunta5" placeholder="Pregunta Nro 5"
                           value=${param.pregunta5}>
                    <% if(marca && ((pregunta5 == null) || (pregunta5.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 5, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta6" class="">Pregunta Nro6</label>
                    <input type="text" class="" id="pregunta6" name="pregunta6" placeholder="Pregunta Nro 6"
                           value=${param.pregunta6}>
                    <% if(marca && ((pregunta6 == null) || (pregunta6.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 6, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta7" class="">Pregunta Nro7</label>
                    <input type="text" class="" id="pregunta7" name="pregunta7" placeholder="Pregunta Nro 7"
                           value=${param.pregunta7}>
                    <% if(marca && ((pregunta7 == null) || (pregunta7.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 7, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta8" class="">Pregunta Nro8</label>
                    <input type="text" class="" id="pregunta8" name="pregunta8" placeholder="Pregunta Nro 8"
                           value=${param.pregunta8}>
                    <% if(marca && ((pregunta8 == null) || (pregunta8.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 8, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta9" class="">Pregunta Nro9</label>
                    <input type="text" class="" id="pregunta9" name="pregunta9" placeholder="Pregunta Nro 9"
                           value=${param.pregunta9}>
                    <% if(marca && ((pregunta9 == null) || (pregunta9.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 9, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class="">
                    <label for="pregunta10" class="">Pregunta Nro10</label>
                    <input type="text" class="" id="pregunta10" name="pregunta10" placeholder="Pregunta Nro 10"
                           value=${param.pregunta10}>
                    <% if(marca && ((pregunta10 == null) || (pregunta10.length() == 0))) { %>
                    <p style="color:red;">La pregunta Nro. 10, no puede estar vacía</p>
                    <% }%>
                </div>

                <br>
                <div class= "">
                    <input type="submit" class="" id="submit" name="submit" value="Enviar" >
                </div>

                <div><input type="hidden" id="marca" name="marca" value="true"></div>

            </form>

            <% } %>

        </div>
    </body>
</html>