package org.lsi2.ebank_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lsi2.ebank_backend.enumes.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
//strategie  table perClasse
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type" , length = 4 , discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class  BanckAccount {
    @Id
    private String id ;
    private double balance ;
    private Date createAt ;
    @Enumerated(EnumType.STRING) //pour enregistrer les types avec les valeurs string
    private AccountStatus status ;
    @ManyToOne
    private Customer customer ;
    @OneToMany(mappedBy = "banckAccount")
    private List<AccountOperation> accountOperations ;

}
