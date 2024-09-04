package org.enset.youness_kaissi_ebankingbackend.services;

import org.enset.youness_kaissi_ebankingbackend.dtos.*;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BalanceNotSufficentException;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BankAccountNotFoundException;
import org.enset.youness_kaissi_ebankingbackend.exceptions.CustomerNotFountException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFountException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFountException;

    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList() ;

    CustomerDTO getCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
