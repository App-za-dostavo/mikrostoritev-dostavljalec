package si.fri.rso.dostavljalec.api.v1.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.dostavljalec.lib.DeliveryPerson;
import si.fri.rso.dostavljalec.services.beans.DeliveryPersonBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class DeliveryPersonMutations {

    @Inject
    private DeliveryPersonBean deliveryPersonBean;

    @GraphQLMutation
    public DeliveryPerson addDeliveryPerson(@GraphQLArgument(name = "deliveryPerson") DeliveryPerson deliveryPerson) {
        deliveryPersonBean.createDeliveryPerson(deliveryPerson);
        return deliveryPerson;
    }

    @GraphQLMutation
    public DeleteResponse deleteDeliveryPerson(@GraphQLArgument(name = "id") Integer id) {
        return new DeleteResponse(deliveryPersonBean.deleteDeliveryPerson(id));
    }

}
