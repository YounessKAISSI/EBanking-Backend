package org.enset.youness_kaissi_ebankingbackend.dtos;

import lombok.Data;
import org.enset.youness_kaissi_ebankingbackend.enums.AccountStatus;

import java.util.Date;

@Data
public class SavingAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
