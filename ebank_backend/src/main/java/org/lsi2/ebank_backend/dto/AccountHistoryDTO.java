package org.lsi2.ebank_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId ;
    private double balance ;
    private int crrentPage ;
    private int totalPages ;
    private int pageSize ;
    private List<AccountOperationDTO> accountOperationDTOS ;
}
