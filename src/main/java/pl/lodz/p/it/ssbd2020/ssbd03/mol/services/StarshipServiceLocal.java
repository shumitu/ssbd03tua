package pl.lodz.p.it.ssbd2020.ssbd03.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipRequestDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.StarshipStatusDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StarshipServiceLocal {

    int getCallCounter();

    int getMaxTransactionCount();

    boolean isLastTransactionRollback();

    List<StarshipDTO> getActiveStarships() throws AppException;

    List<StarshipDTO> getAllStarships() throws AppException;
    
    StarshipDTO getStarshipDetails(StarshipRequestDTO starshipRequestDTO) throws AppException;
    
    StarshipDTO getOperationalStarshipDetails(StarshipRequestDTO starshipRequestDTO) throws AppException;

    void addStarship(StarshipDTO starshipDTO) throws AppException;

    void editStarship(StarshipDTO starshipDTO) throws AppException;

    void changeStarshipActiveStatus(StarshipStatusDTO statusDTO) throws AppException;
}
