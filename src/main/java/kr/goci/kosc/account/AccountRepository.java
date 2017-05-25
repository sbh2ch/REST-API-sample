package kr.goci.kosc.account;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kiost on 2017-05-24.
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByUsername(String username);
}
