package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.OfferForAllListDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OfferForAllListConverter extends AbstractConverter<Offer, OfferForAllListDTO>{
    @Override
    public Offer convert(OfferForAllListDTO dto) {
        return null;
    }

    @Override
    public OfferForAllListDTO convert(Offer entity) throws AppException {
        return new OfferForAllListDTO(entity.getId(), entity.getPrice(), entity.getHidden(), entity.getOpen(),
                entity.getTotalWeight(), entity.getDestination());
    }
}
