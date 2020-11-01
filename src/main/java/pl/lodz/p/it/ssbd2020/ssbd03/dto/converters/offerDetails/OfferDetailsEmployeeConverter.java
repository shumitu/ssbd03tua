package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.offerDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.AbstractConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsEmployeeDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.StarshipOfferDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

public class OfferDetailsEmployeeConverter extends AbstractConverter<Offer, OfferDetailsEmployeeDTO> {
    @Override
    public Offer convert(OfferDetailsEmployeeDTO dto) {
        return null;
    }

    @Override
    public OfferDetailsEmployeeDTO convert(Offer entity) throws AppException {
        if (entity.getStarship() != null) {
            return new OfferDetailsEmployeeDTO(entity.getVersion(), entity.getFlightStartTime(), entity.getFlightEndTime(),
                    entity.getDestination(), entity.getPrice(), entity.getDescription(),
                    new StarshipOfferDetailsDTO(entity.getStarship().getId(), entity.getStarship().getName()),
                    entity.getHidden(), entity.getOpen(), entity.getTotalWeight(), entity.getTotalCost());
        } else {
            return new OfferDetailsEmployeeDTO(entity.getVersion(), entity.getFlightStartTime(), entity.getFlightEndTime(),
                    entity.getDestination(), entity.getPrice(), entity.getDescription(),
                    entity.getHidden(), entity.getOpen(), entity.getTotalWeight(), entity.getTotalCost());
        }
    }
}
