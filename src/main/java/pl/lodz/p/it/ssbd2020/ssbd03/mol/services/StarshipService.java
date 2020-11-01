package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractService;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipStatusDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.StarshipConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Starship;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ConcurrencyException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.StarshipException;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.TransactionException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.StarshipFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.OfferFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HmacSigner;
import pl.lodz.p.it.ssbd2020.ssbd03.utils.StarshipValidator;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipRequestDTO;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class StarshipService extends AbstractService implements SessionSynchronization, StarshipServiceLocal {

    @Inject
    StarshipFacadeLocal starshipFacade;
    @Inject
    OfferFacadeLocal offerFacade;

    /**
     * Metoda pobiera listę wszystkich statków
     * @return lista obiektów DTO, stanowiąca listę statków
     * @author Sergiusz Parchatko
     */
    @Override
    @RolesAllowed(getAllStarships)
    public List<StarshipDTO> getAllStarships() throws AppException {
        try {
            List<StarshipDTO> starshipDTOS = new ArrayList<>();
            StarshipConverter starshipConverter = new StarshipConverter();
            for (Starship starship : starshipFacade.findAll()) {
                starshipDTOS.add(starshipConverter.convert(starship));
            }
            return starshipDTOS;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
 }

    /**
     * Metoda pobiera listę aktywnych statków
     * @return lista obiektów DTO, stanowiąca listę statków
     * @author Sergiusz Parchatko
     */
    @PermitAll
    @Override
    public List<StarshipDTO> getActiveStarships() throws AppException {
        try {
            List<StarshipDTO> starshipDTOS = new ArrayList<>();
            StarshipConverter starshipConverter = new StarshipConverter();
            for (Starship starship : starshipFacade.findAllActiveStarships()) {
                starshipDTOS.add(starshipConverter.convert(starship));
            }
            return starshipDTOS;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }
    
    /**
     * Metoda pobiera szczegóły statku który jest sprawny
     * @param starshipRequestDTO obiekt transferu danych zawierający
     * identyfikator statku, którego dane mają zostać pozyskane
     * @return obiekt DTO, zawierający szczegóły statku
     * @throws AppException wyjątek aplikacyjny gdy nie uda się odnaleźć statku
     * o wskazanym identyfikatorze, bądź odnaleziony statek jest niesprawny
     * @author Paweł Wacławiak 216910
     */
    @Override
    @PermitAll
    public StarshipDTO getOperationalStarshipDetails(StarshipRequestDTO starshipRequestDTO) throws AppException {
        try {
            Starship starship = starshipFacade.findById(starshipRequestDTO.getId());
            if( !starship.getOperational() ) {
                throw StarshipException.createStarshipNotFoundException();
            }
            StarshipConverter converter = new StarshipConverter();
            return converter.convert(starship);
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera szczegóły dowolnego statku
     * @param starshipRequestDTO obiekt transferu danych zawierający
     * identyfikator statku, którego dane mają zostać pozyskane
     * @return obiekt DTO, zawierający szczegóły statku
     * @throws AppException wyjątek aplikacyjny gdy nie uda się odnaleźć statku
     * o wskazanym identyfikatorze
     * @author Paweł Wacławiak 216910
     */
    @Override
    @RolesAllowed(displayStarshipDetails)
    public StarshipDTO getStarshipDetails(StarshipRequestDTO starshipRequestDTO) throws AppException {
        try {
            Starship starship = starshipFacade.findById(starshipRequestDTO.getId());
            StarshipConverter converter = new StarshipConverter();
            return converter.convert(starship);
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda dodaje nowy statek
     *
     * @param starshipDTO obiekt DTO zawierający dane nowego statku
     * @throws AppException wyjątek aplikacyjny, gdy nie uda się dodać statku
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(addStarship)
    public void addStarship(StarshipDTO starshipDTO) throws AppException {
        StarshipConverter starshipConverter = new StarshipConverter();
        StarshipValidator.validateStarshipDTO(starshipDTO);
        try {
            Starship starship = starshipConverter.convert(starshipDTO);
            starshipFacade.create(starship);
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda odpowiada za edycje istniejącego statku
     * @param starshipDTO obiekt DTO zawierający dane statku do edycji
     * @throws AppException wyjątek aplikacyjny gdy wystąpi bląd podczas edycji
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(editStarship)
    public void editStarship(StarshipDTO starshipDTO) throws AppException {
        try {

            Starship starship = starshipFacade.findById(starshipDTO.getId());
            starshipFacade.lockPessimisticRead(starship);

            if (offerFacade.isStarshipAssignedToOffer(starship)) {
                throw StarshipException.createStarshipAssignedToOfferException();
            }
            if (HmacSigner.verifyEtag(starshipDTO.getEtag(), starship.getVersion())) {
                StarshipValidator.validateStarshipDTO(starshipDTO);
                starship.setName(starshipDTO.getName());
                starship.setCrewCapacity(starshipDTO.getCrewCapacity());
                starship.setFuelCapacity(starshipDTO.getFuelCapacity());
                starship.setMaximumSpeed(starshipDTO.getMaximumSpeed());
                starship.setMaximumWeight(starshipDTO.getMaximumWeight());
                starship.setYearOfManufacture(starshipDTO.getYearOfManufacture());
                starshipFacade.edit(starship);
            } else {
                throw ConcurrencyException.createResourceModifiedException();
            }
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda odpowiada za aktywacje / dezaktywacje statku
     * @param statusDTO id statku i nowy status aktywności
     * @throws AppException Wyjątek aplikacyjny gdy nie zmienić stanu aktywności statku
     * @author Michał Tęgi
     */
    @Override
    @RolesAllowed(changeStarshipActiveStatus)
    public void changeStarshipActiveStatus(StarshipStatusDTO statusDTO) throws AppException {

        try {
            Starship starship = starshipFacade.findById(statusDTO.getId());
            starshipFacade.lockPessimisticRead(starship);
            if (statusDTO.isActive() && starship.getOperational()) {
                throw StarshipException.createStarshipAlreadyActiveException();
            } else if (!statusDTO.isActive() && !starship.getOperational()) {
                throw StarshipException.createStarshipAlreadyInActiveException();
            }
            if(offerFacade.isStarshipAssignedToOffer(starship)){
                throw StarshipException.createStarshipAssignedToOfferException();
            }
            starship.setOperational(statusDTO.isActive());
            starshipFacade.edit(starship);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }
}
