package com.webapp;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author manager
 */
public class Borrame {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        EntityManager em = JpaUtil.getEntityManager();
        List<Persona> reg = em.createQuery("select p from Persona p", Persona.class).getResultList();
        reg.forEach(System.out::println);
        em.close();
    }
}
