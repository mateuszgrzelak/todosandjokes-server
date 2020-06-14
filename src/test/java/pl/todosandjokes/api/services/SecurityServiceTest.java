package pl.todosandjokes.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.todosandjokes.api.model.dto.UserDTO;
import pl.todosandjokes.api.model.entity.UserAccount;
import pl.todosandjokes.api.repository.UserAccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class SecurityServiceTest {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private UserAccountRepository userAccountRepository = Mockito.mock(UserAccountRepository.class);
    SecurityService securityService = new SecurityService(userAccountRepository,encoder);

    @BeforeEach
    void setUp(){
        UserAccount firstAccount = new UserAccount();
        firstAccount.setPassword(encoder.encode("password"));
        firstAccount.setEmail("email@wp.pl");
        firstAccount.setActivated(false);
        firstAccount.setUsername("username");
        UserAccount secondAccount = new UserAccount();
        secondAccount.setPassword(encoder.encode("secretpassword"));
        secondAccount.setEmail("email@o2.pl");
        secondAccount.setActivated(false);
        secondAccount.setUsername("user");

        given(userAccountRepository.findByUsername(firstAccount.getUsername())).willReturn(Optional.of(firstAccount));
        given(userAccountRepository.findByUsername(secondAccount.getUsername())).willReturn(Optional.of(secondAccount));
        given(userAccountRepository.findByEmail(firstAccount.getEmail())).willReturn(Optional.of(firstAccount));
        given(userAccountRepository.findByEmail(secondAccount.getEmail())).willReturn(Optional.of(secondAccount));


    }

    @Test
    void createAccountShouldReturnFalse() {
        UserAccount account = new UserAccount();
        account.setPassword("password");
        account.setEmail("email@wp.pl");
        account.setActivated(false);
        account.setUsername("username");
        assertFalse(securityService.createAccount(account));
    }

    @Test
    void createAccountShouldReturnTrue() {
        UserAccount account = new UserAccount();
        account.setPassword("password");
        account.setEmail("anotherEmail@wp.pl");
        account.setActivated(false);
        account.setUsername("newUsername");
        assertTrue(securityService.createAccount(account));
    }

    @Test
    void getUserAccountShouldReturnNotEmptyOptional() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        Optional<UserAccount> userAccount = securityService.getUserAccount(userDTO);
        assertTrue(userAccount.isPresent());
    }

    @Test
    void getUserAccountWithInvalidPasswordShouldReturnEmptyOptional() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username");
        userDTO.setPassword("passwrd");
        Optional<UserAccount> userAccount = securityService.getUserAccount(userDTO);
        assertTrue(userAccount.isEmpty());
    }

}