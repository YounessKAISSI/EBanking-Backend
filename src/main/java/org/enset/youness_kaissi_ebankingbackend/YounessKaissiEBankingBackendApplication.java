package org.enset.youness_kaissi_ebankingbackend;

import org.enset.youness_kaissi_ebankingbackend.dtos.BankAccountDTO;
import org.enset.youness_kaissi_ebankingbackend.dtos.CurrentAccountDTO;
import org.enset.youness_kaissi_ebankingbackend.dtos.CustomerDTO;
import org.enset.youness_kaissi_ebankingbackend.dtos.SavingAccountDTO;
import org.enset.youness_kaissi_ebankingbackend.entities.*;
import org.enset.youness_kaissi_ebankingbackend.enums.AccountStatus;
import org.enset.youness_kaissi_ebankingbackend.enums.OperationType;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BalanceNotSufficentException;
import org.enset.youness_kaissi_ebankingbackend.exceptions.BankAccountNotFoundException;
import org.enset.youness_kaissi_ebankingbackend.exceptions.CustomerNotFountException;
import org.enset.youness_kaissi_ebankingbackend.repositories.AccountOperationRepository;
import org.enset.youness_kaissi_ebankingbackend.repositories.BankAccountRepository;
import org.enset.youness_kaissi_ebankingbackend.repositories.CustomerRepository;
import org.enset.youness_kaissi_ebankingbackend.services.BankAccountService;
import org.enset.youness_kaissi_ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class YounessKaissiEBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YounessKaissiEBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
           //bankService.consulter();
            Stream.of("Youness","Yassine","Hasna").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@outlook.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());

                } catch (CustomerNotFountException e) {
                    e.printStackTrace();
                }
            });

            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();

            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingAccountDTO){
                        accountId = ((SavingAccountDTO) bankAccount).getId();
                    }else{
                        accountId = ((CurrentAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Youness","Yassine","Hasna").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@outlook.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
}
