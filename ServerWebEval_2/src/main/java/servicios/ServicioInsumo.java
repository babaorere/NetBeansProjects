package servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Insumo;
import entity.Response;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 */
@WebService
public class ServicioInsumo {

    @WebMethod(action = "agregarInsumo")
    public String agregarInsumo(int codigo, String nombre, int valor) {
        String salida;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Response r = new Response();

        // Aqui se añadiria el codigo fuente para añadir el insumo
        r.setStatus(200);
        r.setData("Agregado el insumo " + nombre);
        salida = gson.toJson(r);

        return salida;
    }

    @WebMethod(action = "modificarInsumo")
    public String modificarInsumo(int codigo, String nombre, int valor) {
        String salida;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Response r = new Response();

        // Aqui se añadiria el codigo fuente para modificar el insumo
        r.setStatus(200);
        r.setData("Modificado el insumo codigo " + codigo);
        salida = gson.toJson(r);

        return salida;
    }

    @WebMethod(action = "eliminarInsumo")
    public String eliminarInsumo(int codigo) {
        String salida;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Response r = new Response();

        // Aqui se añadiria el codigo fuente para eliminar el insumo
        r.setStatus(200);
        r.setData("Eliminado el insumo codigo " + codigo);
        salida = gson.toJson(r);

        return salida;
    }

    @WebMethod(action = "consultarInsumoByCodigo")
    public String consultarInsumo() {
        String salida;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Response r = new Response();

        // Aqui se añadiria el codigo fuente para consultar el insumo
        // Aqui simula el objeto entregado por la base de datos
        List<Insumo> array = new ArrayList<>();
        array.add(new entity.Insumo(1, "Clavo", 1));
        array.add(new entity.Insumo(2, "Martillo", 1000));
        array.add(new entity.Insumo(3, "Perno", 2));

        r.setStatus(200);
        r.setData(array);
        salida = gson.toJson(r);

        return salida;
    }

}
