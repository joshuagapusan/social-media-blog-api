package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (account.getUsername() != "") {
            if (account.getPassword().length() >= 4) {
                if (accountDAO.getAccountByUsername(account.getUsername()) == null) {
                    return accountDAO.insertAccount(account);
                }
            }
        }

        return null;
    }

    public Account verifyAccount(Account account) {
        if (accountDAO.verifyAccount(account) != null) {
            return accountDAO.verifyAccount(account);
        }

        return null;
    }
}
