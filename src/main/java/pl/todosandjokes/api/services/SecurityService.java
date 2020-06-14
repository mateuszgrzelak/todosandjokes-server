package pl.todosandjokes.api.services;

import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.todosandjokes.api.model.dto.UserDTO;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;

import java.util.Date;
import java.util.Optional;

import static pl.todosandjokes.api.configuration.SecurityConstants.EXPIRATION_TIME;
import static pl.todosandjokes.api.configuration.SecurityConstants.SECRET_KEY;

@Service
public class SecurityService {

    private UserAccountRepository userAccountRepository;
    private BCryptPasswordEncoder encoder;

    public SecurityService(UserAccountRepository userAccountRepository, BCryptPasswordEncoder encoder) {
        this.userAccountRepository = userAccountRepository;
        this.encoder = encoder;
    }

    public boolean createAccount(UserAccount userAccount){
        userAccount.setPassword(encoder.encode(userAccount.getPassword()));
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

    public Optional<UserAccount> getUserAccount(UserDTO user) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(user.getUsername());
        if(userAccount.isPresent() && encoder.matches(user.getPassword(), userAccount.get().getPassword())) {
            return userAccount;
        }
        return Optional.empty();
    }

    public String createJWT(UserAccount account){
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(account.getUsername())
                .setExpiration(new Date(nowMillis + EXPIRATION_TIME))
                .claim("email",account.getEmail())
                .signWith(SECRET_KEY)
                .compact();
    }

}
