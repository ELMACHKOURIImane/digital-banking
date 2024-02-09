package org.lsi2.ebank_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.lsi2.ebank_backend.entities.BanckAccount;
import org.lsi2.ebank_backend.enumes.AccountStatus;
import java.util.Date;

@Data @NoArgsConstructor
public class SavingBanckAccountDTO extends BankAccountDTO {
    private String id ;
    private double balance ;
    private Date createAt ;
    private AccountStatus status ;
    private CustomerDTO customerDTO ;
    private double intrestRate ;

}
