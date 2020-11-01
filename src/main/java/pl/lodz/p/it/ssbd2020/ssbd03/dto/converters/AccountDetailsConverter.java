package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.AccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public final class AccountDetailsConverter extends AbstractConverter<Account, AccountDetailsDTO> {

    @Override
    public Account convert(AccountDetailsDTO dto) {
        return new Account();
    }

    @Override
    public AccountDetailsDTO convert(Account entity) throws AppException {
        List<String> accessLevels = new ArrayList<>();
        for (AccessLevel accessLevel : entity.getAccessLevelCollection()) {
            if(accessLevel.getActive()){
                accessLevels.add(accessLevel.getLevel());
            }
        }
        try {
            return new AccountDetailsDTO(entity.getVersion(), entity.getEmail(), entity.getMotto(), entity.getConfirmed(), entity.getActive(),
                    entity.getPasswordChangeRequired(), accessLevels, entity.getLoginInfo().getLastSuccessfulLogin(), entity.getLoginInfo().getLastUnsuccessfulLogin());
        } catch (NullPointerException e) {
            return new AccountDetailsDTO(entity.getVersion(), entity.getEmail(), entity.getMotto(), entity.getConfirmed(), entity.getActive(),
                    entity.getPasswordChangeRequired(), accessLevels, entity.getLoginInfo().getLastSuccessfulLogin());
        }
    }

}
