package com.bantads.account.account.repository.command;

import com.bantads.account.account.models.command.AccountC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryC extends JpaRepository<AccountC, Long> {
}
