package org.enset.youness_kaissi_ebankingbackend.services;

import org.enset.youness_kaissi_ebankingbackend.entities.BankAccount;
import org.enset.youness_kaissi_ebankingbackend.entities.CurrentAccount;
import org.enset.youness_kaissi_ebankingbackend.entities.SavingAccount;
import org.enset.youness_kaissi_ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter(){
        BankAccount bankAccount=
                bankAccountRepository.findById("99226d3d-36b6-4e35-aba0-c28217ceaaa5").orElse(null);
        if (bankAccount!=null) {
            System.out.println("****************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getId() + "\t" + op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
            });
        }

    }
}
