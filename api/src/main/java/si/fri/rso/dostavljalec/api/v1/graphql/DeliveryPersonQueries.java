package si.fri.rso.dostavljalec.api.v1.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.dostavljalec.lib.DeliveryPerson;
import si.fri.rso.dostavljalec.services.beans.DeliveryPersonBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class DeliveryPersonQueries {

    @Inject
    private DeliveryPersonBean deliveryPersonBean;

    @GraphQLQuery
    public PaginationWrapper<DeliveryPerson> allDeliverers(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                       @GraphQLArgument(name = "sort") Sort sort,
                                                       @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(deliveryPersonBean.getDeliverers(), pagination, sort, filter);
    }

    @GraphQLQuery
    public DeliveryPerson getDeliverers(@GraphQLArgument(name = "id") Integer id) {
        return deliveryPersonBean.getDeliverers(id);
    }

}
