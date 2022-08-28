package com.bantads.account.repository;

import com.bantads.account.model.Account;
import com.bantads.account.model.Transaction;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByAccountId(Long accountId);

    public Transaction findTopByOrderByIdDesc();

    @Query(value = "select t from Transaction t where t.account=?1 and t.timestamp between ?2 and ?3")
    public List<Transaction> findByAccountIdAndTimestampBetween(Account account, Long startDate, Long endDate);
}
