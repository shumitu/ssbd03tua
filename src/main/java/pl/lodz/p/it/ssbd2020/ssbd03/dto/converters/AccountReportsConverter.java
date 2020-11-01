package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.AccountReportDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Account;

public class AccountReportsConverter extends AbstractConverter<Account, AccountReportDTO> {

    @Override
    public Account convert(AccountReportDTO dto) {
        return new Account();
    }

    @Override
    public AccountReportDTO convert(Account entity) {
        return new AccountReportDTO(entity.getEmail(), entity.getLoginInfo().getLastSuccessfulLogin(),
                entity.getLoginInfo().getLastUnsuccessfulLogin(), entity.getLoginInfo().getIpAddress());
    }
}
