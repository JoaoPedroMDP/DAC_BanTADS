package com.bantads.account.transaction.repository;

import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.transaction.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByAccountId(Long accountId);

    public Transaction findTopByOrderByIdDesc();

    @Query(value = "select t from Transaction t where t.account=?1 and t.timestamp between ?2 and ?3")
    public List<Transaction> findByAccountIdAndTimestampBetween(AccountQ account, Long startDate, Long endDate);
}
