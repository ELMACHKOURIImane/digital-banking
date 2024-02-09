package org.lsi2.ebank_backend.web;

import lombok.AllArgsConstructor;
import org.lsi2.ebank_backend.dto.AccountOperationDTO;
import org.lsi2.ebank_backend.dto.CustomerDTO;
import org.lsi2.ebank_backend.entities.AccountOperation;
import org.lsi2.ebank_backend.entities.Customer;
import org.lsi2.ebank_backend.exceptions.CustomerNotFoundException;
import org.lsi2.ebank_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestService {
    private BankAccountService bankAccountService ;

    @GetMapping("/customers")
    public List<CustomerDTO>  customers()
    {
        return this.bankAccountService.listCustomers() ;
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer( @PathVariable Long id) throws CustomerNotFoundException {
    return bankAccountService.getCustomer(id) ;
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)
    {
        System.out.println("Saving customer");
    return bankAccountService.saveCustomer(customerDTO) ;
    }
}
