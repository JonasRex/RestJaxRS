package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
       
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }



    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String jsonContext) {
        PersonDTO m = GSON.fromJson(jsonContext, PersonDTO.class);
        PersonDTO addMovieDTO = new PersonDTO(m.getfName(), m.getlName(), m.getPhone());

        return Response.ok("SUCCESS")
                .entity(GSON.toJson(FACADE.addPerson(addMovieDTO)))
                .build();
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        List<PersonDTO> movies = FACADE.getAllPersons();


        return Response.ok().entity(GSON.toJson(movies)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePersonById(@PathParam("id") long id) {
        PersonDTO pDTO = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(pDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePersonByContext(String jsonContext, @PathParam("id") long id) {
        PersonDTO personToBeChanged = GSON.fromJson(jsonContext, PersonDTO.class);
        personToBeChanged.setId(id);
        PersonDTO updated = FACADE.editPerson(personToBeChanged);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }



}
