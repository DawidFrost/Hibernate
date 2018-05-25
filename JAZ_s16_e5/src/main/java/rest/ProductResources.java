package rest;

import domain.Product;
import domain.ProductCategory;
import domain.ProductComment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@Path("/parts")
@Stateless
public class ProductResources {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll() {

        return em.createNamedQuery("product.all", Product.class).getResultList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Product product) {
        em.persist(product);
        return Response.ok(product.getId()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Product p) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        result.setName(p.getName());
        result.setPrice(p.getPrice());
        em.persist(result);
        return Response.ok().build();
    }

    @GET
    @Path("/byPrice")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getByPrice(@QueryParam("from") double from, @QueryParam("to") double to) {
        List<Product> products = em.createNamedQuery("product.byPrice", Product.class)
                .setParameter("priceFrom", from)
                .setParameter("priceTo", to)
                .getResultList();
        return products;
    }

    @GET
    @Path("/byName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getByName(@QueryParam("name") String name) {
        List<Product> products = em.createNamedQuery("product.byName", Product.class)
                .setParameter("productName", name)
                .getResultList();
        return products;
    }

    @GET
    @Path("/byCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getByCategory(@QueryParam("productCategory") ProductCategory productCategory) {
        List<Product> products = em.createNamedQuery("product.byCategory", Product.class)
                .setParameter("productCategory", productCategory)
                .getResultList();
        return products;
    }

    @POST
    @Path("/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddComments(@PathParam("id") int id, ProductComment productComment) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        } else {
            result.getComments().add(productComment);
            em.persist(result);
        }
        return Response.ok(result.getId()).build();
    }

    @DELETE
    @Path("/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteComment(@PathParam("id") int id, ProductComment p) {
        ProductComment result = em.createNamedQuery("productComment.id", ProductComment.class)
                .setParameter("productCommentId", id)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        } else {

            em.remove(result);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductComment> getAllComments() {
        return em.createNamedQuery("productComment.all", ProductComment.class).getResultList();
    }

}

