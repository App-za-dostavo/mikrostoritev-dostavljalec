package si.fri.rso.dostavljalec.api.v1.resources;

import si.fri.rso.dostavljalec.lib.DeliveryPerson;
import si.fri.rso.dostavljalec.services.beans.DeliveryPersonBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/dostavljalec")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryPersonResource {

    @Inject
    private DeliveryPersonBean deliveryPersonBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getDelivery() {

        List<DeliveryPerson> deliveryPerson = deliveryPersonBean.getDeliverersFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(deliveryPerson).build();
    }

    @GET
    @Path("/{id}")
    public Response getDelivery(@PathParam("id") Integer id) {

        DeliveryPerson deliveryPerson = deliveryPersonBean.getDeliverers(id);

        if (deliveryPerson == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryPerson).build();
    }

    @POST
    public Response createDeliveryPerson(DeliveryPerson person) {

        if (person.getFirstName() == null || person.getAvailability() == null || person.getDistance() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            person = deliveryPersonBean.createDeliveryPerson(person);
        }

        return Response.status(Response.Status.CONFLICT).entity(person).build();
    }

    @PUT
    @Path("{id}")
    public Response putDeliveryPerson(@PathParam("id") Integer id, DeliveryPerson person) {

        person = deliveryPersonBean.putDeliveryPerson(id, person);

        if (person == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDeliveryPerson(@PathParam("id") Integer id) {

        boolean deleted = deliveryPersonBean.deleteDeliveryPerson(id);

        if (deleted) {
            return  Response.status(Response.Status.OK).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }
}

