package org.lsi2.ebank_backend.services;

import org.lsi2.ebank_backend.dto.*;
import org.lsi2.ebank_backend.exceptions.BalanceNotSuffucentExecption;
import org.lsi2.ebank_backend.exceptions.BankAccountNotFoundExeption;
import org.lsi2.ebank_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    //on peut faire l'injection des dependances en utilisant les constructeur avec parametre
    //    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
    //        this.customerRepository = customerRepository;
    //        this.bankAccountRepository = bankAccountRepository;
    //        this.accountOperationRepository = accountOperationRepository;
    //    }
    //en remplacant tous ca avec les annotations Lombok
    CustomerDTO saveCustomer(CustomerDTO customer);

    CurrentBanckAccountDTO saveCurrentBankAccount(double initialBalance , Long customerId , double overDraft) throws CustomerNotFoundException;
    SavingBanckAccountDTO saveSavingBankAccount(double initialBalance , Long customerId , double intrestedRate) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers() ;

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundExeption;

    void debit(String accountId  , double amount , String description) throws BankAccountNotFoundExeption, BalanceNotSuffucentExecption;

    void credit(String accountId, double amount , String description) throws BankAccountNotFoundExeption;

    void transfer(String accountIdSource , String accountIdDestination , double amount) throws BankAccountNotFoundExeption, BalanceNotSuffucentExecption;
    List<BankAccountDTO> bankAccounts() ;

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundExeption;
}
