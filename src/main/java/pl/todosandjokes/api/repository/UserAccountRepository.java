package pl.todosandjokes.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.todosandjokes.api.model.pojo.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByEmail(String email);
}
