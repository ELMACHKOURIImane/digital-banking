package org.lsi2.ebank_backend.exceptions;

public class BankAccountNotFoundExeption extends  Exception{
    public BankAccountNotFoundExeption(String message) {
        super(message);
    }
}
