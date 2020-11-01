package pl.lodz.p.it.ssbd2020.ssbd03.mok.services;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.accountDetails.AccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordAdminDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.changePassword.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.dto.updatedAccountDetails.UpdatedAccountDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.*;

import javax.ejb.Local;
import javax.mail.MessagingException;
import java.util.List;

@Local
public interface AccountServiceLocal {

    List<AccountDTO> getAccountsFiltered(FilterAccountsPhraseDTO filterAccountsPhraseDTO) throws AppException;
    List<AccountReportDTO> getAccountsReports() throws AppException;
    AccountDetailsDTO getOwnAccount() throws AppException;
    AccountDetailsDTO getSomeoneAccount(EmailDTO emailDTO) throws AppException;
    void changeOwnPassword(ChangePasswordDTO changePasswordDTO) throws AppException;
    void changeUsersPassword(ChangePasswordAdminDTO changePasswordDTO) throws AppException, MessagingException;
    void updateOwnAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) throws AppException;
    void updateSomeoneAccount(UpdatedAccountDetailsDTO updatedAccountDetailsDTO) throws AppException;
    LoginTimestampsDTO getLoginTimestampsForOwnAccount() throws AppException;
    void handleSuccessfulLogin(String email) throws AppException;
    void handleUnsuccessfulLogin(String email) throws AppException;
    void createPasswordResetToken(InitResetPasswordDTO initResetPasswordDTO) throws AppException, MessagingException;
    void resetPassword(PasswordResetDTO passwordResetDTO) throws AppException;
    void createNewAccount(RegisterDTO registerDTO) throws AppException, MessagingException;
    void activateAccount(ActivateAccountDTO activateAccountDTO) throws AppException, MessagingException;
    void blockAccount(String email) throws AppException, MessagingException;
    void unblockAccount(String email) throws AppException, MessagingException;
    void sendConfirmationLink(EmailDTO emailDTO) throws AppException, MessagingException;
    void addAccessLevel(AccessLevelDTO accessLevelDTO) throws AppException;
    void revokeAccessLevel(AccessLevelDTO accessLevelDTO) throws AppException;
    void changeAccessLevel(ChangeAccessLevelDTO changeAccessLevelDTO) throws AppException, MessagingException;
    boolean isLastTransactionRollback();
    int getCallCounter();
    int getMaxTransactionCount();
}
