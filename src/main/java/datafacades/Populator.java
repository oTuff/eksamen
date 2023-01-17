package datafacades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.*;
import utils.EMF_Creator;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Populator {
    public static void populate() throws ParseException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "user@gmail.com","test123");
        User admin = new User("admin", "admin@gmail.com","test123");
        CityInfo city1 = new CityInfo(2750,"Ballerup");
        CityInfo city2 = new CityInfo(2800,"Kongens Lyngby");
        Address a1 = new Address("Sankt Jacobsvej",city1);
        Address a2 = new Address("Nørgaardsvej",city2);


        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        user.setAddress(a1);
        admin.addRole(adminRole);
        admin.setAddress(a2);
        em.persist(a1);
        em.persist(a2);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }
    
    public static void main(String[] args) throws ParseException {
        populate();
    }
}
