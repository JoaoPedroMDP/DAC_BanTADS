package com.bantads.account.account.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.bantads.account.account.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Account findByUserId(Integer userId);
}
