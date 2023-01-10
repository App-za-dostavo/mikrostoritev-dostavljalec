package si.fri.rso.dostavljalec.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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

@Log
@ApplicationScoped
@Path("/dostavljalec")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, HEAD, DELETE, OPTIONS")
public class DeliveryPersonResource {

    @Inject
    private DeliveryPersonBean deliveryPersonBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery people", summary = "All delivery people")
    @APIResponse(responseCode = "200", description = "Successfully received a list of delivery people",
                    content = @Content(schema = @Schema(implementation = DeliveryPerson.class)))
    @GET
    public Response getDelivery() {

        List<DeliveryPerson> deliveryPerson = deliveryPersonBean.getDeliverersFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(deliveryPerson).build();
    }


    @Operation(description = "Get a list of a delivery person by id", summary = "One delivery person")
    @APIResponse(responseCode = "200", description = "Successfully received delivery person by id")
    @GET
    @Path("/{id}")
    public Response getDelivery(@PathParam("id") Integer id) {

        DeliveryPerson deliveryPerson = deliveryPersonBean.getDeliverers(id);

        if (deliveryPerson == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryPerson).build();
    }

    @Operation(description = "Post a new delivery person", summary = "New delivery person")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Successfully added a new one"
        ),
            @APIResponse(responseCode = "405", description = "Validation error")

    })
    @POST
    public Response createDeliveryPerson(@RequestBody(description = "Object with delivery person metadata",
            required = true, content = @Content(
                    schema = @Schema(implementation = DeliveryPerson.class))) DeliveryPerson person) {

        if (person.getFirstName() == null || person.getAvailability() == null || person.getLatitude() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            person = deliveryPersonBean.createDeliveryPerson(person);
        }

        return Response.status(Response.Status.CONFLICT).entity(person).build();
    }

    @Operation(description = "Change properties of one person", summary = "Put method on a person")
    @APIResponse(responseCode = "200", description = "Successfully changed properties")
    @PUT
    @Path("{id}")
    public Response putDeliveryPerson(@Parameter(description = "Person id", required = true) @PathParam("id") Integer id,
                                      @RequestBody(description = "Object with person metadata",
                                      required = true, content = @Content(schema = @Schema(implementation = DeliveryPerson.class)
                                      )) DeliveryPerson person) {

        person = deliveryPersonBean.putDeliveryPerson(id, person);

        if (person == null) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @Operation(description = "Delete a delivery person", summary = "Delete method on a person")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Successfully deleted a delivery person by id"
        ),
        @APIResponse(responseCode = "404", description = "Person by this id not found")
    })
    @DELETE
    @Path("{id}")
    public Response deleteDeliveryPerson(@Parameter(description = "Person id", required = true) @PathParam("id") Integer id) {

        boolean deleted = deliveryPersonBean.deleteDeliveryPerson(id);

        if (deleted) {
            return  Response.status(Response.Status.NO_CONTENT).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }
}

