package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.core.AbstractService;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.ApplicationConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.converters.ReviewConverter;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.ApplicationException;
import pl.lodz.p.it.ssbd2020.ssbd03.interceptors.LoggingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.enterprise.SecurityContext;


import pl.lodz.p.it.ssbd2020.ssbd03.entities.Candidate;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Offer;
import pl.lodz.p.it.ssbd2020.ssbd03.mol.facades.OfferFacadeLocal;
import pl.lodz.p.it.ssbd2020.ssbd03.security.services.HmacSigner;
import pl.lodz.p.it.ssbd2020.ssbd03.utils.ApplicationValidator;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class ApplicationService extends AbstractService implements SessionSynchronization, ApplicationServiceLocal {

    @Inject
    ReviewFacadeLocal reviewFacade;

    @Inject
    OfferFacadeLocal offerFacade;

    @Inject
    ApplicationFacadeLocal applicationFacade;

    @Inject
    CategoryFacadeLocal categoryFacade;

    @Inject
    SecurityContext securityContext;

    @Inject
    CandidateFacadeLocal candidateFacade;

    @Inject
    EmployeeFacadeLocal employeeFacade;

    @Resource(name = "ACCEPTED_CATEGORY")
    String acceptedCategory;

    /**
     * Metoda dodaje zgłoszenie kandydata na ofertę lotu.
     *
     * @param applicationDTO obiekt DTO zwierający informacje o zgłoszeniu
     * @throws AppException wyjątek aplikacyjny gdy nie uda się dodać zgłoszenia
     * @author Paweł Wacławiak 216910
     */
    @Override
    @RolesAllowed(applyToOffer)
    public void createNewApplication(NewApplicationDTO applicationDTO) throws AppException{
        if ( !ApplicationValidator.isExaminationCodeValid(applicationDTO.getExaminationCode()) ) {
            throw ApplicationException.incorrectExaminationCodeException();
        }
        if ( !ApplicationValidator.isMotivationalLetterValid(applicationDTO.getMotivationalLetter()) ) {
            throw ApplicationException.incorrectMotivationalLetterException();
        }
        if ( !ApplicationValidator.isWeightValid(applicationDTO.getWeight()) ) {
            throw ApplicationException.incorrectWeightException();
        }
        try {
            Offer offer = offerFacade.findById(applicationDTO.getOfferId());
            Candidate candidate = candidateFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            List<Application> applications = applicationFacade.findByCandidate(candidate);

            offerFacade.lockPessimisticRead(offer);
            for (Application application : applications) {
                if ( application.getOffer().equals(offer) ) {
                    throw ApplicationException.alreadyAppliedException();
                }
            }        
            
            if ( !applicationDTO.getOfferEtag().equals(HmacSigner.getEtag(offer.getVersion())) ) {
                throw ApplicationException.offerHasChangedException();
            }
            
            if ( !offer.getOpen() ) {
                throw ApplicationException.offerClosedException();
            }
            
            Application application = new Application(applicationDTO.getWeight(),
                applicationDTO.getExaminationCode(), applicationDTO.getMotivationalLetter(), 
                new Date(), offer, candidate);
            
            applicationFacade.create(application);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera pełną listę zgłoszeń do danej oferty
     *
     * @param offerId Identyfikator oferty, dla której chcemy wyświetlić listę zgłoszeń
     * @return Lista zawierająca DTO wszystkich zgłoszeń dla danej oferty
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania listy złoszeń z bazy danych
     * @author Jakub Fornalski
     */

    @Override
    @RolesAllowed({listApplications})
    public List<ApplicationListedDTO> getOfferApplicationsList(Long offerId) throws AppException {

        try {
            Offer offer = offerFacade.findById(offerId);
            ArrayList<ApplicationListedDTO> applicationDTOs = new ArrayList<ApplicationListedDTO>();
            for (Application application : offer.getApplicationCollection()) {
                try {
                    applicationDTOs.add(new ApplicationListedDTO(application.getId(), application.getCandidate().getFirstName(),
                            application.getCandidate().getLastName(), application.getWeight(), application.getCreatedTime(),
                            true, reviewFacade.findByApplication(application).getCategory().getName()));
                } catch (NotYetReviewedException e) {
                    applicationDTOs.add(new ApplicationListedDTO(application.getId(), application.getCandidate().getFirstName(),
                            application.getCandidate().getLastName(), application.getWeight(), application.getCreatedTime(),
                            false, ""));
                }
            }
            Collections.sort(applicationDTOs);
            return applicationDTOs;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera pełną listę zgłoszeń aktualnie zalogowanego użytkownika
     *
     * @return Lista zawierająca DTO wszystkich zgłoszeń danego użytkownika
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania listy złoszeń z bazy danych
     * @author Jakub Fornalski
     */

    @Override
    @RolesAllowed({listOwnApplications})
    public List<ApplicationCandidateListedDTO> getOwnApplicationsList() throws AppException {
        try {
            Candidate candidate = candidateFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            ArrayList<ApplicationCandidateListedDTO> applicationDTOs = new ArrayList<ApplicationCandidateListedDTO>();
            for (Application application : applicationFacade.findByCandidate(candidate)) {
                try {
                    applicationDTOs.add(new ApplicationCandidateListedDTO(application.getId(), application.getCreatedTime(), true,
                            reviewFacade.findByApplication(application).getCategory().getName(), application.getOffer().getDestination(),
                            application.getOffer().getFlightStartTime(), application.getOffer().getFlightEndTime()));
                } catch (NotYetReviewedException e) {
                    applicationDTOs.add(new ApplicationCandidateListedDTO(application.getId(), application.getCreatedTime(), false,
                            "", application.getOffer().getDestination(), application.getOffer().getFlightStartTime(),
                            application.getOffer().getFlightEndTime()));
                }
            }
            Collections.sort(applicationDTOs);
            return applicationDTOs;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda powoduje przypisania wybranego zgłoszenia do wskazanej kategorii
     *
     * @param applicationCategoryDTO DTO zawierające informacje o tym którę zgłoszenie
     *                               do jakiej kategorii ma zostać przypisane
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem przypisywania zgłoszenia do kategorii,
     * spowodowanym np. istnieniem już oceny dla danego zgłoszenia
     * @author Jakub Fornalski
     */

    @Override
    @RolesAllowed(addApplicationToCategory)
    public void addApplicationToCategory(ApplicationCategoryDTO applicationCategoryDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(applicationCategoryDTO.getApplicationId());
            applicationFacade.lockPessimisticRead(application);

            if (!HmacSigner.verifyEtag(applicationCategoryDTO.getEtag(), application.getVersion()))
                throw ReviewException.reviewApplicationChanged();

            if (reviewFacade.checkIfReviewExists(application))
                throw ReviewException.reviewAlreadyExistsException();

            Category category = categoryFacade.findByName(applicationCategoryDTO.getCategoryName());
            Employee employee = employeeFacade.findByEmail(securityContext.getCallerPrincipal().getName());
            Review review = new Review(application, category, employee);
            reviewFacade.create(review);
            Offer offer = review.getApplication().getOffer();
            offerFacade.lockPessimisticRead(offer);
            double weight = recalculateTotalWeight(reviewFacade.findByOffer(offer));
            if (weight > offer.getStarship().getMaximumWeight())
                throw ReviewException.reviewExceedsWeightLimit();
            offer.setTotalWeight(weight);
        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pomocnicza, zliczająca łączną wagę z zatwierdzonych zgłoszeń, na podstawie listy recenzji
     *
     * @param reviews Lista zgłoszeń do oferty, której całkowita waga ma zostać obliczona
     * @return łączna waga dla danej oferty
     * @author Jakub Fornalski
     */

    private double recalculateTotalWeight(List<Review> reviews) {
        double result = 0.0;
        for (Review review : reviews) {
            if (review.getCategory().getName().equalsIgnoreCase(acceptedCategory))
                result += review.getApplication().getWeight();
        }
        return Math.floor(result * 100) / 100;
    }

    /**
     * Metoda odpowiedzialna jest za edycję własnego zgłoszenia
     *
     * @param updateApplicationDTO jest to obiekt DTO zawierający wagę kandydata,
     *                              numer badania lekarskiego,
     *                              a także odnośnik do listu motywacyjnego kandydata.
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem edycji zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(editApplication)
    public void updateApplication(UpdateApplicationDTO updateApplicationDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(updateApplicationDTO.getApplicationId());
            applicationFacade.lockPessimisticRead(application);

            if (!securityContext.getCallerPrincipal().getName().equals(application.getCandidate().getAccount().getEmail())) {
                throw ApplicationException.applicationNotFoundException();
            }

            if (reviewFacade.checkIfReviewExists(application)) {
                throw ApplicationException.applicationHasReviewException();
            }

            if (HmacSigner.verifyEtag(updateApplicationDTO.getEtag(), application.getVersion())) {
                if (!ApplicationValidator.isMotivationalLetterValid(updateApplicationDTO.getMotivationalLetter())) {
                    throw ApplicationException.incorrectMotivationalLetterException();
                }
                if (!ApplicationValidator.isExaminationCodeValid(updateApplicationDTO.getExaminationCode())) {
                    throw ApplicationException.incorrectExaminationCodeException();
                }
                if (!ApplicationValidator.isWeightValid(updateApplicationDTO.getWeight())) {
                    throw ApplicationException.incorrectWeightException();
                }

                application.setWeight(updateApplicationDTO.getWeight());
                application.setExaminationCode(updateApplicationDTO.getExaminationCode());
                application.setMotivationalLetter(updateApplicationDTO.getMotivationalLetter());
                applicationFacade.edit(application);
            } else {
                throw ConcurrencyException.createResourceModifiedException();
            }

        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera status zgłoszenia w celu wyświetlenia go w odpowiednim komponencie, należącym do widoku
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * oceny zgłoszenia z bazy danych
     * @return obiekt DTO zawierający status danego zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(checkReview)
    public ReviewDTO getReview(IdDTO idDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(idDTO.getId());
            try {
                Review review = reviewFacade.findByApplication(application);
                ReviewConverter reviewConverter = new ReviewConverter();
                ReviewDTO reviewDTO = reviewConverter.convert(review);
                return reviewDTO;
            } catch (NotYetReviewedException e) {
                throw ReviewException.reviewNotFoundException();
            }


        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda pobiera status zgłoszenia w celu wyświetlenia go w odpowiednim komponencie, należącym do widoku
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego stan chcemy wyświetlić
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * oceny zgłoszenia z bazy danych
     * @return obiekt DTO zawierający status danego zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(checkMyReview)
    public ReviewDTO getMyReview(IdDTO idDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(idDTO.getId());

            if (!securityContext.getCallerPrincipal().getName().equals(application.getCandidate().getAccount().getEmail())) {
                throw ApplicationException.applicationNotFoundException();
            }

            try {
                Review review = reviewFacade.findByApplication(application);
                ReviewConverter reviewConverter = new ReviewConverter();
                ReviewDTO reviewDTO = reviewConverter.convert(review);
                return reviewDTO;
            } catch (NotYetReviewedException e) {
                throw ReviewException.reviewNotFoundException();
            }


        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda służąca do anulowania własnego zgłoszenia.
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      które chcemy anulować
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem anulowania zgłoszenia
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(cancelApplication)
    public void cancelApplication(IdDTO idDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(idDTO.getId());
            applicationFacade.lockPessimisticRead(application);

            if (!securityContext.getCallerPrincipal().getName().equals(application.getCandidate().getAccount().getEmail())) {
                throw ApplicationException.applicationNotFoundException();
            }

            if (reviewFacade.checkIfReviewExists(application)) {
                throw ApplicationException.applicationHasReviewException();
            }

            applicationFacade.remove(application);

        } catch (EJBTransactionRolledbackException | PersistenceException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda służąca do wyświetlenia szczegółów zgłoszenia
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego dane chcemy wyświetlić
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * szczegółów zgłoszenia z bazy danych
     * @return obiekt DTO zawierający szczegóły zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(displayApplicationDetails)
    public ApplicationDTO displayApplicationDetails(IdDTO idDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(idDTO.getId());

            ApplicationConverter applicationConverter = new ApplicationConverter();
            ApplicationDTO applicationDTO = applicationConverter.convert(application);

            try {
                applicationDTO.setCategoryName(reviewFacade.findByApplication(application).getCategory().getName());
            } catch (NotYetReviewedException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            }

            return applicationDTO;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }

    /**
     * Metoda służąca do wyświetlenia szczegółów zgłoszenia
     *
     * @param idDTO jest to obiekt DTO zawierający identyfikator zgłoszenia,
     *                      którego dane chcemy wyświetlić
     * @throws AppException wyjątek aplikacyjny związany z niepowodzeniem pobrania
     * szczegółów zgłoszenia z bazy danych
     * @return obiekt DTO zawierający szczegóły zgłoszenia
     *
     * @author 216727 Ernest Błaż
     */
    @Override
    @RolesAllowed(displayMyApplicationDetails)
    public ApplicationDTO getMyApplication(IdDTO idDTO) throws AppException {
        try {
            Application application = applicationFacade.findById(idDTO.getId());

            if (!securityContext.getCallerPrincipal().getName().equals(application.getCandidate().getAccount().getEmail())) {
                throw ApplicationException.applicationNotFoundException();
            }

            ApplicationConverter applicationConverter = new ApplicationConverter();
            ApplicationDTO applicationDTO = applicationConverter.convert(application);

            try {
                applicationDTO.setCategoryName(reviewFacade.findByApplication(application).getCategory().getName());
            } catch (NotYetReviewedException e) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            }

            return applicationDTO;
        } catch (EJBTransactionRolledbackException e) {
            throw TransactionException.createExceptionTransactionRollback();
        }
    }
}
