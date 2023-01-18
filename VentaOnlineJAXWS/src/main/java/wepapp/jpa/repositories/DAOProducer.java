package wepapp.jpa.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import webapp.jpa.entities.Persona;

/**
 *
 */
class DaoProducer {

    @PersistenceContext
    private EntityManager em;

    @Produces
    @ApplicationScoped
    private GenericDAO<Persona, Integer> producePersonaDAO() {
        return new GenericDAOImpl<>(em, Persona.class);
    }

}
