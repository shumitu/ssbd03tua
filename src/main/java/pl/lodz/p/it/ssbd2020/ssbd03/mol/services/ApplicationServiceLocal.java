package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ApplicationServiceLocal {

    void createNewApplication(NewApplicationDTO applicationDTO) throws AppException;

    List<ApplicationListedDTO> getOfferApplicationsList(Long offerId) throws AppException;

    List<ApplicationCandidateListedDTO> getOwnApplicationsList() throws AppException;

    void addApplicationToCategory(ApplicationCategoryDTO applicationCategoryDTO) throws AppException;

    void updateApplication(UpdateApplicationDTO updatedApplicationDTO) throws AppException;

    void cancelApplication(IdDTO idDTO) throws AppException;

    ReviewDTO getReview(IdDTO idDTO) throws AppException;

    ReviewDTO getMyReview(IdDTO idDTO) throws AppException;

    ApplicationDTO displayApplicationDetails(IdDTO idDTO) throws AppException;

    ApplicationDTO getMyApplication(IdDTO idDTO) throws AppException;

    int getCallCounter();

    int getMaxTransactionCount();

    boolean isLastTransactionRollback();
}
