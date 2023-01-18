package datafacades;

import entities.Rental;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RentalFacade {
    private static EntityManagerFactory emf;
    private static RentalFacade instance;

    private RentalFacade() {
    }

    public static RentalFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Rental> getRentalsByTenant(Integer tenantId) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r INNER JOIN r.tenants t WHERE t.id=:tenantId", Rental.class);
            query.setParameter("tenantId", tenantId);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any rentals of for that tenant in the system", 404, e);
        }
    }
}
