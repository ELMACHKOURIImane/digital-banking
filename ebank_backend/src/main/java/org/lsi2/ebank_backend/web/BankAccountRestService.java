package org.lsi2.ebank_backend.web;

import lombok.AllArgsConstructor;
import org.lsi2.ebank_backend.dto.AccountHistoryDTO;
import org.lsi2.ebank_backend.dto.AccountOperationDTO;
import org.lsi2.ebank_backend.dto.BankAccountDTO;
import org.lsi2.ebank_backend.exceptions.BankAccountNotFoundExeption;
import org.lsi2.ebank_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestService {
    private BankAccountService bankAccountService ;
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundExeption {
      return this.bankAccountService.getBankAccount(accountId) ;
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getBankAccounts() throws BankAccountNotFoundExeption {
        return this.bankAccountService.bankAccounts() ;
    }

    @GetMapping("/accounts/{accountId}/operations")
    public  List<AccountOperationDTO> getHistory(@PathVariable String accountId)
    {
        return this.bankAccountService.accountHistory(accountId) ;
    }

    @GetMapping("/accounts/{accountId}/history")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId ,
                                               @RequestParam(name = "page" , defaultValue = "0") int page ,
                                               @RequestParam(name = "size" , defaultValue = "0") int size ) throws BankAccountNotFoundExeption {
        return this.bankAccountService.getAccountHistory(accountId , page , size);
    }



}
