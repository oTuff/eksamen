package datafacades;

import entities.Address;
import entities.House;
import entities.Rental;
import entities.Tenant;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

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

    public List<Rental> getAllRentals() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r", Rental.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any Rentals in the system", 404, e);
        }
    }

    public Rental getRentalById(Integer id) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            Rental rental = em.find(Rental.class, id);
            return rental;
        } catch (Exception e) {
            throw new API_Exception("Can't find a user with the username: " + id, 404, e);
        }

    }

    public List<House> getAllHouses() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<House> query = em.createQuery("SELECT h FROM House h", House.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any Houses in the system", 404, e);
        }
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

    public Rental createRental(Rental rental) throws API_Exception {
        EntityManager em = getEntityManager();
        House house = rental.getHouseHouse();
        try {
            em.getTransaction().begin();
            //checks for Tenants
            for (Tenant t : rental.getTenants()) {
                if (t.getId() == null) {
                    em.persist(t);
                } else {
                    em.find(Tenant.class, t.getId());
                }
            }
            //checks for address
            if (house.getAddress().getId() == null) {
                em.persist(house.getAddress());
            } else {
                em.find(Address.class, house.getAddress().getId());
            }
            //checks for house
            if (house.getId() == null) {
                em.persist(house);
            } else {
                em.find(House.class, house.getId());
            }
            em.persist(rental);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("error creating rental agreement" + rental);
        } finally {
            em.close();
        }
        return rental;
    }


    public Rental updateRental(Rental rental) throws API_Exception {
        EntityManager em = getEntityManager();
        House house = rental.getHouseHouse();
        try {
            em.getTransaction().begin();
            //checks for Tenants
            for (Tenant t : rental.getTenants()) {
                if (t.getId() == null) {
                    em.persist(t);
                } else {
                    em.merge(t);
                }
            }
            //checks for address
            if (house.getAddress().getId() == null) {
                em.persist(house.getAddress());
            } else {
                em.merge(house.getAddress());
            }
            //checks for house
            if (house.getId() == null) {
                em.persist(house);
            } else {
                em.merge(house);
            }
            em.merge(rental);
            System.out.println(rental);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("error updating rental agreement" + rental);
        } finally {
            em.close();
        }
        return rental;
    }

    public House updateHouse(House house) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (Rental r : house.getRentals()) {
                for (Tenant t : r.getTenants()) {
                    if (t.getId() == null) {
                        em.persist(t);
                    } else {
                        em.merge(t);
                    }
                }
                if (r.getId() == null) {
                    em.persist(r);
                } else {
                    em.merge(r);
                }
            }
            if (house.getAddress().getId() == null) {
                em.persist(house.getAddress());
            } else {
                em.merge(house.getAddress());
            }
            em.persist(house);
            em.getTransaction().commit();
        } catch (
                Exception e) {
            throw new API_Exception("error updating house");
        } finally {
            em.close();
        }
        return house;
    }

    public Rental deleteRental(Integer id) throws API_Exception {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, id);

        try {
            em.getTransaction().begin();
            em.remove(rental);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (rental == null) {
                throw new API_Exception("no rental with that id", 404, e);
            }
        } finally {
            em.close();
        }
        return rental;
    }

}
