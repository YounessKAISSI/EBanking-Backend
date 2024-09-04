package org.enset.youness_kaissi_ebankingbackend.repositories;

import org.enset.youness_kaissi_ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
