package org.lsi2.ebank_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lsi2.ebank_backend.entities.BanckAccount;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id ;
    private String name ;
    private String email ;

}
