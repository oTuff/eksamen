package businessfacades;

import dtos.RentalDTO;
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

class RentalDTOFacadeTest {
    private static EntityManagerFactory emf;
    private static RentalDTOFacade facade;

    RentalDTO rdto1, rdto2;
    Tenant t1, t2;
    Rental r1, r2, r3;
    House h1, h2;
    CityInfo c1, c2;
    Address a1, a2;

    public RentalDTOFacadeTest() throws ParseException {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RentalDTOFacade.getInstance(emf);
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
        r1.addTenant(t1);

        r2.setRentalStartDate(LocalDate.of(2022, 1, 1));
        r2.setRentalEndDate(LocalDate.of(2024, 1, 1));
        r2.setRentalPriceAnnual(2000);
        r2.setRentalDeposit(200);
        r2.setRentalContactPerson("Andersine");
        r2.setHouseHouse(h2);
        r2.addTenant(t2);

        r3.setRentalStartDate(LocalDate.of(2023, 2, 1));
        r3.setRentalEndDate(LocalDate.of(2024, 2, 1));
        r3.setRentalPriceAnnual(1000);
        r3.setRentalDeposit(100);
        r3.setRentalContactPerson("Anders And");
        r3.setHouseHouse(h1);

        t1.setTenantName("Oscar");
        t1.setTenantPhone(12345678);
        t1.setTenantJob("revisor");
//        t1.addRental(r1);

        t2.setTenantName("Alexander");
        t2.setTenantPhone(88888888);
        t2.setTenantJob("programmer");
//        t2.addRental(r1);
//        t2.addRental(r2);
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
            rdto1 = new RentalDTO(r1);
            rdto2 = new RentalDTO(r2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void getAllRentals() throws API_Exception{
        List<RentalDTO> actual = facade.getAllRentals();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

    @Test
    void getRentalById() throws API_Exception{
        RentalDTO actual = facade.getRentalById(r1.getId());
        RentalDTO expected = rdto1;
        System.out.println(actual);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getRentalsByTenat() throws API_Exception {
        List<RentalDTO> actual = facade.getRentalsByTenant(t1.getId());
        int expected = 1;
        assertEquals(expected, actual.size());
    }

    @Test
    public void createRental() throws API_Exception {
        RentalDTO rentalDTO = new RentalDTO(r1);
        rentalDTO.setId(null);
        facade.createRental(rentalDTO);
//        assertNotNull(rentalDTO.getId());
        int actualSize = facade.getAllRentals().size();
        assertEquals(3, actualSize);
    }

    @Test
    public void updateRental() throws API_Exception {
        Rental rental = r1;
        rental.setRentalContactPerson("nykontaktperson");
        RentalDTO updatedRentalDTO = facade.updateRental(new RentalDTO(rental));
        assertNotNull(rental.getId());
        RentalDTO actual = facade.getRentalById(r1.getId());
        assertEquals("nykontaktperson", rental.getRentalContactPerson());
        assertEquals("nykontaktperson", actual.getRentalContactPerson());
    }
    @Test
    public void updateRentalAndTenant() throws API_Exception {
        Rental rental = r1;
        Set<Tenant> tenants = new HashSet<>();
        tenants.add(t2);
        rental.setTenants(tenants);
        RentalDTO updatedRentalDTO = facade.updateRental(new RentalDTO(rental));
        assertNotNull(rental.getId());
        RentalDTO actual = facade.getRentalById(r1.getId());
        assertEquals(new RentalDTO.TenantDto(t2), updatedRentalDTO.getTenants().toArray()[0]);
        assertEquals(new RentalDTO.TenantDto(t2), actual.getTenants().toArray()[0]);
        assertNotEquals(1,t1.getRentals().size());
    }


    @Test
    public void deleteRental() throws API_Exception{
        facade.deleteRental(r1.getId());
        int actualSize = facade.getAllRentals().size();
        assertEquals(1, actualSize);
    }

//    @Test
//    public void createRentalAndTenant() throws API_Exception {
//        Rental rental = r1;
//        rental.setId(null);
//        for (Tenant t : rental.getTenants()) {
//            t.setId(null);
//        }
//        facade.createRental(rental);
//        assertNotNull(rental.getId());
//        int actualSize = facade.getAllRentals().size();
//        assertEquals(3, actualSize);
//    }
//
//    @Test
//    public void createRentalAndHouse() throws API_Exception {
//        Rental rental = r1;
//        rental.setId(null);
//        House house = new House();
//        house.setAddress(a1);
//        house.setHouseNumberOfRooms(1);
//        rental.setHouseHouse(house);
//        facade.createRental(rental);
//        assertNotNull(rental.getId());
//        int actualSize = facade.getAllRentals().size();
//        assertEquals(3, actualSize);
//    }

}