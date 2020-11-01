package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.AccountDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;

import java.util.ArrayList;
import java.util.List;

public final class AccountConverter extends AbstractConverter<Account, AccountDTO> {

    @Override
    public Account convert(AccountDTO dto) {
        return new Account();
    }

    @Override
    public AccountDTO convert(Account entity) {
        List<String> accessLevels = new ArrayList<>();
        for (AccessLevel accessLevel : entity.getAccessLevelCollection()) {
            if(accessLevel.getActive()) {
                accessLevels.add(accessLevel.getLevel());
            }
        }
        return new AccountDTO(entity.getEmail(), entity.getConfirmed(), entity.getActive(), entity.getPasswordChangeRequired(), accessLevels);
    }


}
