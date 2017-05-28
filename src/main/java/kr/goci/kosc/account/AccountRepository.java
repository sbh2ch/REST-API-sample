package kr.goci.kosc.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by kiost on 2017-05-24.
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByUsername(String username);

    //@Query("select password from account where username = ?1 and username = ?2")
    Account findByUsernameAndPassword(String username, String password);
}
