package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.offerDetails;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.AbstractConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.OfferDetailsCandidateDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.offerDetails.StarshipOfferDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

public class OfferDetailsCandidateConverter extends AbstractConverter<Offer, OfferDetailsCandidateDTO> {
    @Override
    public Offer convert(OfferDetailsCandidateDTO dto) {
        return null;
    }

    @Override
    public OfferDetailsCandidateDTO convert(Offer entity) throws AppException {
        if (entity.getStarship() != null) {
            return new OfferDetailsCandidateDTO(entity.getVersion(), entity.getFlightStartTime(), entity.getFlightEndTime(),
                    entity.getDestination(), entity.getPrice(), entity.getDescription(),
                    new StarshipOfferDetailsDTO(entity.getStarship().getId(), entity.getStarship().getName()));
        } else {
            return new OfferDetailsCandidateDTO(entity.getVersion(), entity.getFlightStartTime(), entity.getFlightEndTime(),
                    entity.getDestination(), entity.getPrice(), entity.getDescription());
        }
    }

}
