package com.bantads.account.account.repository.query;

import com.bantads.account.account.models.query.AccountQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryQ extends JpaRepository<AccountQ, Long> {
    public AccountQ findByUserId(Integer userId);
}
