package com.bantads.account.repository;

import com.bantads.account.model.Account;
import org.springframework.data.jpa.repository.*;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByUserId(Integer userId);
}
