package si.fri.rso.dostavljalec.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.dostavljalec.lib.DeliveryPerson;
import si.fri.rso.dostavljalec.models.converters.DeliveryPersonConverter;
import si.fri.rso.dostavljalec.models.entities.DeliveryPersonEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryPersonBean {

    @Inject
    private EntityManager em;

    public List<DeliveryPerson> getDeliverers() {
        TypedQuery<DeliveryPersonEntity> query = em.createNamedQuery("DeliveryPersonEntity.getAll", DeliveryPersonEntity.class);

        List<DeliveryPersonEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryPersonConverter::toDto).collect(Collectors.toList());
    }

    public List<DeliveryPerson> getDeliverersFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, DeliveryPersonEntity.class, queryParameters).stream()
                .map(DeliveryPersonConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryPerson getDeliverers(Integer id) {

        DeliveryPersonEntity deliveryPersonEntity = em.find(DeliveryPersonEntity.class, id);

        if (deliveryPersonEntity == null) {
            throw new NotFoundException();
        }

        DeliveryPerson deliveryPerson = DeliveryPersonConverter.toDto(deliveryPersonEntity);

        return deliveryPerson;
    }

    public DeliveryPerson createDeliveryPerson(DeliveryPerson deliveryPerson) {

        DeliveryPersonEntity deliveryPersonEntity = DeliveryPersonConverter.toEntity(deliveryPerson);

        try {
            beginTx();
            em.persist(deliveryPersonEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (deliveryPersonEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryPersonConverter.toDto(deliveryPersonEntity);
    }

    public DeliveryPerson putDeliveryPerson(Integer id, DeliveryPerson deliveryPerson) {

        DeliveryPersonEntity person = em.find(DeliveryPersonEntity.class, id);

        if (person == null) {
            return null;
        }

        DeliveryPersonEntity updatedDeliveryPersonEntity = DeliveryPersonConverter.toEntity(deliveryPerson);

        try {
            beginTx();
            updatedDeliveryPersonEntity.setId(person.getId());
            updatedDeliveryPersonEntity = em.merge(updatedDeliveryPersonEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return DeliveryPersonConverter.toDto(updatedDeliveryPersonEntity);
    }

    public boolean deleteDeliveryPerson(Integer id) {

        DeliveryPersonEntity deliveryPerson = em.find(DeliveryPersonEntity.class, id);

        if (deliveryPerson != null) {
            try {
                beginTx();
                em.remove(deliveryPerson);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
