package com.bantads.account.transaction.repository.query;

import com.bantads.account.account.models.query.AccountQ;
import com.bantads.account.transaction.models.query.TransactionQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositoryQ extends JpaRepository<TransactionQ, Long> {
    public List<TransactionQ> findByAccountId(Long accountId);

    public TransactionQ findTopByOrderByIdDesc();

    @Query(value = "select t from TransactionQ t where t.account=?1 and t.timestamp between ?2 and ?3")
    public List<TransactionQ> findByAccountIdAndTimestampBetween(AccountQ account, Long startDate, Long endDate);
}
