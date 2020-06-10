package pl.todosandjokes.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.todosandjokes.api.model.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByEmail(String email);
}
