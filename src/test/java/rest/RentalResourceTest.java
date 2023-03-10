package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RentalResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    RentalDTO rdto1, rdto2;
    Tenant t1, t2;
    Rental r1, r2, r3;
    House h1, h2;
    CityInfo c1, c2;
    Address a1, a2;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/rentals").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/rentals")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/rentals")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testPrintResponse() {
        Response response = given().when().get("/rentals/" + r1.getId());
        ResponseBody body = response.getBody();
        System.out.println(body.prettyPrint());

        response
                .then()
                .assertThat()
                .body("rentalDeposit", equalTo(100));
    }

    @Test
    void allHouses() {
        List<HouseDTO> houseDTOS;
        houseDTOS = given()
                .contentType("application/json")
                .when()
                .get("/rentals/houses")
                .then()
                .extract().body().jsonPath().getList("", HouseDTO.class);
        r1.addTenant(t1);
        System.out.println(houseDTOS);
//        assertThat(houseDTOS, containsInAnyOrder(new HouseDTO(h1)));
        assertEquals(2, houseDTOS.size());
    }

    @Test
    void allRentals() {
        List<RentalDTO> rentalDTOList;
        rentalDTOList = given()
                .contentType("application/json")
                .when()
                .get("/rentals")
                .then()
                .extract().body().jsonPath().getList("", RentalDTO.class);
//        r1.addTenant(t1);
        System.out.println(rentalDTOList);
//        assertThat(rentalDTOList, containsInAnyOrder(rdto1));
//        assertThat(rentalDTOList, containsInAnyOrder(new RentalDTO(r1)));
        assertEquals(2, rentalDTOList.size());
    }


    @Test
    void getRentalById() {
        given()
                .contentType(ContentType.JSON)
                .get("/rentals/" + r1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(r1.getId()))
                .body("rentalDeposit", equalTo(100));
    }

//    @Test
//    void getRentalsByTenant() {
//        List<RentalDTO> rentalDTOS;
//        rentalDTOS = given().contentType("application/json")
//                .when().get("/rentals/tenant/" + t1.getId())
//                .then().extract().body().jsonPath().getList("", RentalDTO.class);
//
//        assertThat(rentalDTOS, containsInAnyOrder(rdto1));
//    }

    @Test
    void creatRental() {
        RentalDTO rdto = new RentalDTO(r1);
        rdto.setId(null);
        String requestBody = GSON.toJson(rdto);
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/rentals")
                .then()
                .assertThat()
                .statusCode(200)
                .body("rentalDeposit", equalTo(100));
    }

    @Test
    void updateRental() {
        Rental rental = r1;
        rental.setRentalContactPerson("nykontaktperson");
        String requestBody = GSON.toJson(new RentalDTO(r1));
        System.out.println(requestBody);
        given().header("Content-type", ContentType.JSON)
                .and().body(requestBody).when().put("/rentals")
                .then().assertThat().statusCode(200)
                .body("rentalContactPerson", equalTo("nykontaktperson"));
    }

    //    @Test
    public void updateRentalAndTenant() {
        Rental rental = r1;
        Set<Tenant> tenants = new HashSet<>();
        tenants.add(t2);
        rental.setTenants(tenants);
        String requestBody = GSON.toJson(new RentalDTO(r1));
        given().header("Content-type", ContentType.JSON)
                .and().body(requestBody).when().put("/rentals")
                .then().assertThat().statusCode(200)
//                .body("tenants", equalTo("[{id="+t2.getId()+", tenantName=Alexander, tenantPhone=88888888, tenantJob=programmer}]"));

                .body("tenants", contains(new RentalDTO.TenantDto(t2)));
    }

    @Test
    void deleteRental() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", r1.getId())
                .delete("/rentals/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(r1.getId()));
    }

//    @Test
//    void creatBoat() {
//        Boat boat = b1;
//        boat.setId(null);
//        BoatDTO bdto = new BoatDTO(boat);
//        String requestBody = GSON.toJson(bdto);
//        System.out.println(requestBody);

//        test:
//        String requestBody="{\n" +
//                "  \"boatBrand\": \"saab\",\n" +
//                "  \"boatMake\": \"test\",\n" +
//                "  \"boatName\": \"test123\",\n" +
//                "  \"boatImage\": \"test\",\n" +
//                "  \"harbour\": {\n" +
//                "    \"id\": 1,\n" +
//                "    \"harbourName\": \"test harbour\",\n" +
//                "    \"harbourCapacity\": 2,\n" +
//                "    \"addressAddress\": {\n" +
//                "      \"id\": 1,\n" +
//                "      \"streetAddress\": \"sankt jacobsvej\",\n" +
//                "      \"cityInfo\": {\n" +
//                "        \"zipCode\": 2750,\n" +
//                "        \"cityName\": \"Ballerup\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"owners\": [\n" +
//                "    {\n" +
//                "      \"id\": 1,\n" +
//                "      \"ownerName\": \"Oscar\",\n" +
//                "      \"ownerPhone\": 12345678,\n" +
//                "      \"address\": {\n" +
//                "        \"id\": 1,\n" +
//                "        \"streetAddress\": \"sankt jacobsvej\",\n" +
//                "        \"cityInfo\": {\n" +
//                "          \"zipCode\": 2750,\n" +
//                "          \"cityName\": \"Ballerup\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

//        given().header("Content-type", ContentType.JSON)
//                .and().body(requestBody).when().post("/boats")
//                .then().assertThat().statusCode(200)
//                .body("boatName", equalTo("test123"));
//    }
//
//    @Test
//    void updateBoat() {
//        bdto1.setBoatName("updated");
//        String requestBody = GSON.toJson(bdto1);
//        System.out.println(requestBody);
//        given().header("Content-type", ContentType.JSON)
//                .and().body(requestBody).when().put("/boats")
//                .then().assertThat().statusCode(200)
//                .body("boatName", equalTo("updated"));
//    }

}