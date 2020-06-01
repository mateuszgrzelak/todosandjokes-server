package pl.todosandjokes.api.services.authorization;

import org.springframework.stereotype.Service;
import pl.todosandjokes.api.model.pojo.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;

import java.util.Optional;

@Service
public class Security {

    UserAccountRepository userAccountRepository;

    public Security(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public boolean createAccount(UserAccount userAccount){
        Optional<UserAccount> account = userAccountRepository.findByUsername(userAccount.getUsername());
        if(account.isPresent()){
            return false;
        }
        account = userAccountRepository.findByEmail(userAccount.getEmail());
        if(account.isPresent()){
            return false;
        }
        userAccountRepository.save(userAccount);
        return true;
    }

}
