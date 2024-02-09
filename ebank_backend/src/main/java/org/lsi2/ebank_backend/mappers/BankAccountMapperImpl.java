package org.lsi2.ebank_backend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.lsi2.ebank_backend.dto.AccountOperationDTO;
import org.lsi2.ebank_backend.dto.CurrentBanckAccountDTO;
import org.lsi2.ebank_backend.dto.CustomerDTO;
import org.lsi2.ebank_backend.dto.SavingBanckAccountDTO;
import org.lsi2.ebank_backend.entities.AccountOperation;
import org.lsi2.ebank_backend.entities.CurrentAcount;
import org.lsi2.ebank_backend.entities.Customer;
import org.lsi2.ebank_backend.entities.SavingAcount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO formCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO() ;
        BeanUtils.copyProperties(customer ,customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        return customerDTO ;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO)
    {
        Customer customer = new Customer() ;
        BeanUtils.copyProperties(customerDTO , customer);
        return customer ;
    }

    public SavingBanckAccountDTO fromSavingBankAccount(SavingAcount savingAcount)
    {
        SavingBanckAccountDTO savingBanckAccountDTO = new SavingBanckAccountDTO() ;

        BeanUtils.copyProperties(savingAcount , savingBanckAccountDTO);

        savingBanckAccountDTO.setCustomerDTO(formCustomer(savingAcount.getCustomer()));
        savingBanckAccountDTO.setType(savingAcount.getClass().getSimpleName());
        return  savingBanckAccountDTO ;
    }
    public SavingAcount fromSavingBankAccountDTO(SavingBanckAccountDTO savingAcountdto)
    {
        SavingAcount savingAcount = new SavingAcount() ;
        BeanUtils.copyProperties(savingAcountdto , savingAcount);
        savingAcount.setCustomer(fromCustomerDTO(savingAcountdto.getCustomerDTO()));
        return savingAcount ;
    }

    public CurrentBanckAccountDTO fromCurrentBankAccount(CurrentAcount currentAcount)
    {
        CurrentBanckAccountDTO currentBanckAccountDTO = new CurrentBanckAccountDTO() ;
        BeanUtils.copyProperties(currentAcount,currentBanckAccountDTO);
        currentBanckAccountDTO.setCustomerDTO(formCustomer(currentAcount.getCustomer()));
        currentBanckAccountDTO.setType(currentAcount.getClass().getSimpleName());
        return currentBanckAccountDTO ;
    }
    public CurrentAcount fromCurrentBankAccountDTO(CurrentBanckAccountDTO currentBanckAccountDTO)
    {

        CurrentAcount currentAcount = new CurrentAcount() ;
        BeanUtils.copyProperties(currentBanckAccountDTO , currentAcount);
        currentAcount.setCustomer(fromCustomerDTO(currentBanckAccountDTO.getCustomerDTO()));

        return currentAcount ;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation)
    {
     AccountOperationDTO accountOperationDTO =  new AccountOperationDTO() ;
     BeanUtils.copyProperties(accountOperation , accountOperationDTO);

     return accountOperationDTO ;
    }

}
