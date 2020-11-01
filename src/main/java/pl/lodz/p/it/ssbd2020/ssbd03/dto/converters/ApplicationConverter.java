package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ApplicationDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Application;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Singleton;

public class ApplicationConverter extends AbstractConverter<Application, ApplicationDTO> {
    @Override
    public Application convert(ApplicationDTO dto) {
        return new Application();
    }

    @Override
    public ApplicationDTO convert(Application entity) throws AppException {
        return new ApplicationDTO(entity.getVersion(), entity.getId(), entity.getWeight(), entity.getExaminationCode(),
                entity.getMotivationalLetter(), entity.getOffer().getId(), entity.getOffer().getDestination(),
                entity.getCandidate().getFirstName(), entity.getCandidate().getLastName());
    }
}
