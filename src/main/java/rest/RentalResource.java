package rest;

import businessfacades.RentalDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.RentalFacade;
import dtos.RentalDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("rentals")
public class RentalResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private RentalDTOFacade rentalDTOFacade = RentalDTOFacade.getInstance(EMF);
//    private RentalFacade rentalFacade = RentalFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTenats() throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getAllRentals())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalById(@PathParam("id") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getRentalById(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/tenant/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRentalsByTenatId(@PathParam("tenantId") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getRentalsByTenant(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) throws API_Exception {
        RentalDTO rentalDTO= GSON.fromJson(content, RentalDTO.class);
        RentalDTO newRentalDTO = rentalDTOFacade.createRental(rentalDTO);
        return Response.ok().entity(GSON.toJson(newRentalDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(String content) throws API_Exception {
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        RentalDTO updateRentalDTO = rentalDTOFacade.updateRental(rentalDTO);
        return Response.ok().entity(GSON.toJson(updateRentalDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @DELETE
    @Path("/{rentalId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteRental(@PathParam("rentalId") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.deleteRental(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}