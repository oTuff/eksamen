package rest;

import businessfacades.RentalDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.RentalFacade;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("homes")
public class HouseResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private RentalDTOFacade rentalDTOFacade = RentalDTOFacade.getInstance(EMF);
//    private RentalFacade rentalFacade = RentalFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTenats() throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getAllHouses())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}
