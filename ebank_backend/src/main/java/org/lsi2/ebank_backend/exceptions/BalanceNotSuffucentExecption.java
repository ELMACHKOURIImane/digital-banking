package org.lsi2.ebank_backend.exceptions;

public class BalanceNotSuffucentExecption extends Exception {
    public BalanceNotSuffucentExecption(String balanceNotSufficient) {
    super(balanceNotSufficient);
    }
}
