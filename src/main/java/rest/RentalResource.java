package rest;

import businessfacades.RentalDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.RentalFacade;
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
    private RentalFacade rentalFacade = RentalFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllBoats() throws API_Exception {
//        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getAllBoats())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
//    }

    @GET
    @Path("/{tenantId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOwnersByBoat(@PathParam("tenantId") Integer id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getRentalsByTenant(id))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response create(String content) throws API_Exception {
//        BoatDTO boatDTO = GSON.fromJson(content, BoatDTO.class);
//        BoatDTO newBoatDTO = rentalDTOFacade.createBoat(boatDTO);
//        return Response.ok().entity(GSON.toJson(newBoatDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
//    }
//
//    @PUT
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response update(String content) throws API_Exception {
//        BoatDTO boatDTO = GSON.fromJson(content, BoatDTO.class);
//        BoatDTO updatedBoatDTO = rentalDTOFacade.updateBoat(boatDTO);
//        return Response.ok().entity(GSON.toJson(updatedBoatDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
//    }
}