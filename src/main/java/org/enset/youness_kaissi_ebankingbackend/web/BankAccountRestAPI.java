package org.enset.youness_kaissi_ebankingbackend.web;

import org.enset.youness_kaissi_ebankingbackend.dtos.*;
import org.enset.youness_kaissi_ebankingbackend.entities.BankAccount;
import org.enset.youness_kaissi_ebankingbackend.entities.Customer;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BalanceNotSufficentException;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BankAccountNotFoundException;
import org.enset.youness_kaissi_ebankingbackend.services.BankAccountService;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }


    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public TransferDTO transfer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.transfer(transferDTO.getAccountSource(),transferDTO.getAccountDestination(),transferDTO.getAmount());
        return transferDTO;
    }
}
