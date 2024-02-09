package org.lsi2.ebank_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lsi2.ebank_backend.dto.*;
import org.lsi2.ebank_backend.entities.*;
import org.lsi2.ebank_backend.enumes.OperationTypes;
import org.lsi2.ebank_backend.exceptions.BalanceNotSuffucentExecption;
import org.lsi2.ebank_backend.exceptions.BankAccountNotFoundExeption;
import org.lsi2.ebank_backend.exceptions.CustomerNotFoundException;
import org.lsi2.ebank_backend.mappers.BankAccountMapperImpl;
import org.lsi2.ebank_backend.repositorys.AccountOperationRepository;
import org.lsi2.ebank_backend.repositorys.BankAccountRepository;
import org.lsi2.ebank_backend.repositorys.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository ;
    private BankAccountRepository bankAccountRepository ;
    private AccountOperationRepository accountOperationRepository ;
    private BankAccountMapperImpl DTOMapper ;

//on peut faire l'injection des dependances en utilisant les constructeur avec parametre
//    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
//        this.customerRepository = customerRepository;
//        this.bankAccountRepository = bankAccountRepository;
//        this.accountOperationRepository = accountOperationRepository;
//    }
//en remplacant tous ca avec les annotations Lombok
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = DTOMapper.fromCustomerDTO(customerDTO);
      Customer savedCustomer = this.customerRepository.save(customer);
        return DTOMapper.formCustomer(savedCustomer);
    }

    @Override
    public CurrentBanckAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        log.info("Saving new Account");
        Customer customer = this.customerRepository.findById(customerId).orElse(null) ;
        if(customer==null)
        {
            throw new CustomerNotFoundException("Customer not found " );
        }
        CurrentAcount banckAccount = new CurrentAcount() ;
        banckAccount.setId(UUID.randomUUID().toString());
        banckAccount.setCreateAt(new Date());
        banckAccount.setBalance(initialBalance);
        banckAccount.setCustomer(customer);
        banckAccount.setOverDraft(overDraft);
        CurrentAcount savedCurrentAccount =  this.bankAccountRepository.save(banckAccount) ;

        return  DTOMapper.fromCurrentBankAccount(savedCurrentAccount) ;
    }

    @Override
    public SavingBanckAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double intrestedRate) throws CustomerNotFoundException {
        log.info("Saving new Account");
        Customer customer = this.customerRepository.findById(customerId).orElse(null) ;
        if(customer==null)
        {
            throw new CustomerNotFoundException("Customer not found " );
        }
        SavingAcount banckAccount = new SavingAcount() ;
        banckAccount.setId(UUID.randomUUID().toString());
        banckAccount.setCreateAt(new Date());
        banckAccount.setBalance(initialBalance);
        banckAccount.setCustomer(customer);
        banckAccount.setInterstRate(intrestedRate);
        SavingAcount savingAcount =   this.bankAccountRepository.save(banckAccount) ;

        return DTOMapper.fromSavingBankAccount(savingAcount) ;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
       List<CustomerDTO> customerDTOS =  customers.stream().map(cust->DTOMapper.formCustomer(cust)).collect(Collectors.toList()) ;

        return customerDTOS ;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundExeption {
      BanckAccount banckAccount = this.bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundExeption("Bank Account not found"));
      if(banckAccount instanceof SavingAcount)
      {
          SavingAcount savingAcount = (SavingAcount) banckAccount ;
          return DTOMapper.fromSavingBankAccount(savingAcount) ;
      }
      else {
          CurrentAcount currentAcount = (CurrentAcount) banckAccount ;

          return DTOMapper.fromCurrentBankAccount(currentAcount) ;
      }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExeption, BalanceNotSuffucentExecption {

        BanckAccount banckAccount = this.bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundExeption("Bank Account not found"));

        if(banckAccount.getBalance() < amount){
            throw new BalanceNotSuffucentExecption("balance not sufficient") ;
        }
        AccountOperation accountOperation = new AccountOperation() ;
        accountOperation.setType(OperationTypes.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setBanckAccount(banckAccount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        this.accountOperationRepository.save(accountOperation) ;
        banckAccount.setBalance(banckAccount.getBalance()-amount);
        this.bankAccountRepository.save(banckAccount) ;
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExeption {
        BanckAccount banckAccount = this.bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundExeption("Bank Account not found"));
        AccountOperation accountOperation = new AccountOperation() ;
        accountOperation.setType(OperationTypes.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setBanckAccount(banckAccount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        this.accountOperationRepository.save(accountOperation) ;
        banckAccount.setBalance(banckAccount.getBalance()-amount);
        this.bankAccountRepository.save(banckAccount) ;
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExeption, BalanceNotSuffucentExecption {

        debit(accountIdSource , amount , "Transfert to "+accountIdDestination);
        credit(accountIdDestination , amount , "Transfert from" +accountIdSource);

    }

    @Override
    public List<BankAccountDTO> bankAccounts() {
        List<BanckAccount> banckAccounts = this.bankAccountRepository.findAll() ;
       List<BankAccountDTO> bankAccountDTOS =  banckAccounts.stream().map(banckAccount -> {
            if(banckAccount instanceof  SavingAcount)
            {
                SavingAcount savingAcount = (SavingAcount) banckAccount ;
                return DTOMapper.fromSavingBankAccount(savingAcount) ;
            }
            else{
                CurrentAcount currentAcount = (CurrentAcount) banckAccount ;
                return  DTOMapper.fromCurrentBankAccount(currentAcount) ;
            }
        }).collect(Collectors.toList()) ;
       return  bankAccountDTOS ;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
   Customer customer =   this.customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not Found")) ;
  return DTOMapper.formCustomer(customer) ;
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId)
    {
        List<AccountOperation>  accountOperations = this.accountOperationRepository.findByBanckAccountId(accountId) ;

       return accountOperations.stream().map(op->DTOMapper.fromAccountOperation(op)).collect(Collectors.toList()) ;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundExeption {

        BanckAccount banckAccount = this.bankAccountRepository.findById(accountId).orElse(null) ;
        if(banckAccount == null) throw new BankAccountNotFoundExeption("Account Not Found") ;
        Page<AccountOperation>  accountOperations = this.accountOperationRepository.findByBanckAccountId(accountId ,PageRequest.of(page , size)) ;

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO() ;

        List<AccountOperationDTO>  accountOperationDTOS = accountOperations.stream().map(op -> DTOMapper.fromAccountOperation(op)).collect(Collectors.toList()) ;
        accountHistoryDTO.setAccountId(banckAccount.getId());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setBalance(banckAccount.getBalance());
        accountHistoryDTO.setCrrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return  accountHistoryDTO;
    }


}
