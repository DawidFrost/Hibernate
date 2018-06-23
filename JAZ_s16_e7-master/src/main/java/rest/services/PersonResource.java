package rest.services;

import domain.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Collection;
import java.util.List;


@Stateless
@Path("people")
public class PersonResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll(@QueryParam("page") int page){
        int start = 0;
        if(page > 0) {
            start = (page - 1) * 8;
        }
        return em.createNamedQuery("person.all", Person.class)
                .setFirstResult(start)
                .setMaxResults(start + 8)
                .getResultList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Person person) {
        em.persist(person);
        return Response.ok(person.getId()).build();
    }

    @PUT
    @Path( "/{id}" )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update( @PathParam( "id" ) int personId , Person p) {
        Person result = em.createNamedQuery("person.id", Person.class)
                .setParameter("personId", personId)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        result.setFirstName(p.getFirstName());
        result.setLastName(p.getLastName());
        result.setLastName(p.getLastName());
        result.setGender(p.getGender());
        result.setAge(p.getAge());
        result.setEmail(p.getEmail());
        result.setBirthday(p.getBirthday());
        em.persist(result);
        return Response.ok().build();
    }

    @DELETE @Path( "/{id}" )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete( @PathParam( "id" ) int personId) {
        Person result = em.createNamedQuery("person.id", Person.class)
                .setParameter("personId", personId)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
            em.remove(result);

        return Response.ok().build();
    }

}
