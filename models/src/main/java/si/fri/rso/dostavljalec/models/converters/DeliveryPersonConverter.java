package si.fri.rso.dostavljalec.models.converters;

import si.fri.rso.dostavljalec.lib.DeliveryPerson;
import si.fri.rso.dostavljalec.models.entities.DeliveryPersonEntity;

public class DeliveryPersonConverter {

    public static DeliveryPerson toDto(DeliveryPersonEntity entity) {

        DeliveryPerson dto = new DeliveryPerson();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMeansOfTransport(entity.getMeansOfTransport());
        dto.setAvailability(entity.getAvailability());
        dto.setDistance(entity.getDistance());

        return dto;
    }

    public static DeliveryPersonEntity toEntity(DeliveryPerson dto) {

        DeliveryPersonEntity entity = new DeliveryPersonEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMeansOfTransport(dto.getMeansOfTransport());
        entity.setAvailability(dto.getAvailability());
        entity.setDistance(dto.getDistance());

        return entity;
    }
}
