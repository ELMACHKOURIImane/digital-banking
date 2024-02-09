package org.lsi2.ebank_backend.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
//for the table per classe stratgy the discriminatorvalue is never used
@DiscriminatorValue("CA")
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAcount extends BanckAccount {
    double overDraft ;
}
