package com.webapp.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author manager
 */
@WebService(serviceName = "NewWS")
public class NewWS {

    @WebMethod(operationName = "hello")
//    public String hello(@WebParam(name = "name") String txt) {
    public String hello() {
        return "Hello " + " !";
    }
}
