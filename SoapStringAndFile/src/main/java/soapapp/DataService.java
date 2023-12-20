package soapapp;

import org.springframework.stereotype.Service;

/**
 * Clase general para procesar la informacion enviada al servicio SOAP
 */
@Service
public class DataService {

    /**
     * Constructor por defecto
     */
    public DataService() {
    }

    /**
     * Aqui se procesan los datos
     *
     * @param str
     * @param data
     * @return
     */
    public String service(String str, byte[] data) {

        return "XXX";

    }

}
