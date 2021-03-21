package com.rd.credit.score.engine.service;

import com.rd.credit.score.engine.entity.CreditScore;

public interface CreditScoreService {
    public Integer checkCreditScore(String ssnNumber);
    public Integer getCreditScore(CreditScore creditScore);
}
