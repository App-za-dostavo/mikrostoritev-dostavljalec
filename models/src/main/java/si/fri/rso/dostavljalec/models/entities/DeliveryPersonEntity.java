package si.fri.rso.dostavljalec.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "dostavljalec")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryPersonEntity.getAll", query = "SELECT person FROM DeliveryPersonEntity person"),
                @NamedQuery(name = "DeliveryPersonEntity.getById", query = "SELECT person FROM DeliveryPersonEntity person WHERE person.id=:id")
        })
public class DeliveryPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "meansOfTransport")
    private String meansOfTransport;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "distance")
    private Float distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMeansOfTransport() {
        return meansOfTransport;
    }

    public void setMeansOfTransport(String meansOfTransport) {
        this.meansOfTransport = meansOfTransport;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}
