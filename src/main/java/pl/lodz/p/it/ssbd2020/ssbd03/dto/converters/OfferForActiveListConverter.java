package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.OfferForActiveListDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OfferForActiveListConverter extends AbstractConverter<Offer, OfferForActiveListDTO> {
    @Override
    public Offer convert(OfferForActiveListDTO dto) {
        return null;
    }

    @Override
    public OfferForActiveListDTO convert(Offer entity) throws AppException {
        return new OfferForActiveListDTO(entity.getId(), entity.getPrice(), entity.getOpen(), entity.getDestination());
    }
}
