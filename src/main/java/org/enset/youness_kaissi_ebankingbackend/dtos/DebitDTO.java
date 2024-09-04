package org.enset.youness_kaissi_ebankingbackend.dtos;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
