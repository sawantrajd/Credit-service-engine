package com.rd.loancalculator.exception;

public class DaysLimitConstraintCrossedException extends Exception{

    private static final long serialVersionUID = 1L;

    public DaysLimitConstraintCrossedException(){super();}
    public DaysLimitConstraintCrossedException(String errorMessage) {
        super(errorMessage);
    }
}
