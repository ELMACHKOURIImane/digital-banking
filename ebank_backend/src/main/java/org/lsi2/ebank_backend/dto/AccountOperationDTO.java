package org.lsi2.ebank_backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lsi2.ebank_backend.entities.BanckAccount;
import org.lsi2.ebank_backend.enumes.OperationTypes;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id ;
    private Date operationDate ;
    private double amount ;
    private OperationTypes type;
    private BanckAccount banckAccount ;
    String description ;


}
