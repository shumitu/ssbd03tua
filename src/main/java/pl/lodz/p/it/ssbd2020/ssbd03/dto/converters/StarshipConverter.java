package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

/**
 * Konwerter encji Starship i klasy dto StarshipDTO
 */
public final class StarshipConverter extends AbstractConverter<Starship, StarshipDTO> {

    /**
     * Metoda konwertuje obiekt klasy StarshipDTO na obiekt klasy encyjnej Starship
     * @param dto obiekt DTO
     * @return obiekt klasy encyjnej
     */
    @Override
    public Starship convert(StarshipDTO dto) {
        System.out.println(dto.getMaximumSpeed());
        return new Starship(dto.getName(), dto.getCrewCapacity(), dto.getMaximumWeight(), dto.getFuelCapacity(),
                dto.getMaximumSpeed(), dto.getYearOfManufacture(), dto.isOperational());
    }

    /**
     * Metoda konwertuje obiekt klasy encyjnej Starship na obiekt klasy StarshipDTO
     * @param entity obiekt klasy encyjnej
     * @return obiekt DTO
     */
    @Override
    public StarshipDTO convert(Starship entity) throws AppException {
        return new StarshipDTO(entity.getVersion(), entity.getId(), entity.getName(), entity.getCrewCapacity(), entity.getMaximumWeight(), entity.getFuelCapacity(),
                entity.getMaximumSpeed(), entity.getYearOfManufacture(), entity.getOperational());
    }
}
