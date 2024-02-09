package org.lsi2.ebank_backend.exceptions;

public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(String customer_not_found) {
        super(customer_not_found);
    }

}
