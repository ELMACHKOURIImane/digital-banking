package org.lsi2.ebank_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lsi2.ebank_backend.enumes.OperationTypes;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date operationDate ;
    private double amount ;
    @Enumerated(EnumType.STRING)
    private OperationTypes type;
    @ManyToOne
    private BanckAccount banckAccount ;
    String description ;


}
