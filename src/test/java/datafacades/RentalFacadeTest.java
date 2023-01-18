package datafacades;

import entities.*;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RentalFacadeTest {
    private static EntityManagerFactory emf;
    private static RentalFacade facade;

    Tenant t1, t2;
    Rental r1, r2, r3;
    House h1, h2;
    CityInfo c1, c2;
    Address a1, a2;

    public RentalFacadeTest() throws ParseException {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        t1 = new Tenant();
        t2 = new Tenant();
        r1 = new Rental();
        r2 = new Rental();
        r3 = new Rental();
        h1 = new House();
        h2 = new House();
        c1 = new CityInfo(2750, "Ballerup");
        c2 = new CityInfo(2800, "Lyngby");
        a1 = new Address("sankt jacobsvej", c1);
        a2 = new Address("norgardsvej", c2);

        h1.setHouseNumberOfRooms(4);
        h1.setAddress(a1);

        h2.setHouseNumberOfRooms(2);
        h2.setAddress(a2);

        r1.setRentalStartDate(LocalDate.of(2023, 2, 1));
        r1.setRentalEndDate(LocalDate.of(2024, 2, 1));
        r1.setRentalPriceAnnual(1000);
        r1.setRentalDeposit(100);
        r1.setRentalContactPerson("Anders And");
        r1.setHouseHouse(h1);

        r2.setRentalStartDate(LocalDate.of(2022, 1, 1));
        r2.setRentalEndDate(LocalDate.of(2024, 1, 1));
        r2.setRentalPriceAnnual(2000);
        r2.setRentalDeposit(200);
        r2.setRentalContactPerson("Andersine");
        r2.setHouseHouse(h2);

        r3.setRentalStartDate(LocalDate.of(2023, 2, 1));
        r3.setRentalEndDate(LocalDate.of(2024, 2, 1));
        r3.setRentalPriceAnnual(1000);
        r3.setRentalDeposit(100);
        r3.setRentalContactPerson("Anders And");
        r3.setHouseHouse(h1);

        t1.setTenantName("Oscar");
        t1.setTenantPhone(12345678);
        t1.setTenantJob("revisor");
        t1.addRental(r1);

        t2.setTenantName("Alexander");
        t2.setTenantPhone(88888888);
        t2.setTenantJob("programmer");
//        t2.addRental(r1);
        t2.addRental(r2);
//        t2.addRental(r3);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();
            em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.persist(h1);
            em.persist(h2);
            em.persist(r1);
            em.persist(r2);
//            em.persist(r3);
            em.persist(t1);
            em.persist(t2);
            em.getTransaction().commit();
        } finally {
//            em.find(Rental.class,r1.getId());
//            em.find(Rental.class,r2.getId());
////            em.find(Tenant.class,t2.getId());
//            t2.addRental(r1);
//            em.merge(t2);
//            em.merge(r2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getRentalsByTenant() throws API_Exception {
//        System.out.println(t1);
//        System.out.println(t2);
//        System.out.println(r1);
//        System.out.println(r2);
//        System.out.println(h1);
//        System.out.println(h2);
        List<Rental> actual = facade.getRentalsByTenant(t1.getId());
        int expected = 1;
        assertEquals(expected, actual.size());
        assertEquals(true, actual.contains(r1));
    }

    //todo fix cant add/get multiple rentals in test???
//    @Test
//    public void getRentalByTenantMultiple() throws API_Exception {
//        List<Rental> actual = facade.getRentalsByTenant(t2.getId());
//        System.out.println(t2);
//        System.out.println(actual);
//        int expected = 2;
//        assertEquals(expected, actual.size());
//    }


//    @Test
//    public void getAllOwnersByBoat() throws API_Exception {
//        List<Owner> actual = facade.getAllOwnersByBoat(b1.getId());
//        int expected = 1;
//        assertEquals(expected, actual.size());
//    }
//
//    @Test
//    public void createBoat() throws API_Exception {
//        Set<Owner> ownerSet = new HashSet<>();
//        ownerSet.add(o1);
//        Boat boat = new Boat("saab","test", "dengodebad","img",h1, ownerSet);
//        System.out.println(facade.createBoat(boat));
//        assertNotNull(boat.getId());
//        int actualSize = facade.getAllBoats().size();
//        assertEquals(2,actualSize);
//    }

//    @Test
//    public void updateBoat() throws API_Exception{
//        Set<Owner> ownerSet = new HashSet<>();
//        ownerSet.add(o2);
//        Boat boat = b1;
//        b1.setOwners(ownerSet);
//        facade.updateBoat(boat);
//        assertNotNull(boat.getId());
//        int actualSize = facade.getAllBoats().size();
//        assertEquals(1,actualSize);
//        assertEquals(o2,b1.getOwners().toArray()[0]);
//    }

//    @Test
//    public void updateBoatKeepOwner() throws API_Exception{
//        Set<Owner> ownerSet = new HashSet<>();
//        ownerSet.add(o2);
//        ownerSet.add(o1);
//        b1.setOwners(ownerSet);
//        facade.updateBoat(b1);
//        assertNotNull(b1.getId());
//        int actualSize = facade.getAllBoats().size();
//        assertEquals(1,actualSize);
//        assertEquals(2,b1.getOwners().size());
//    }
}