package com.bantads.account.transaction.repository.command;

import com.bantads.account.transaction.models.command.TransactionC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryC extends JpaRepository<TransactionC, Long> {}
