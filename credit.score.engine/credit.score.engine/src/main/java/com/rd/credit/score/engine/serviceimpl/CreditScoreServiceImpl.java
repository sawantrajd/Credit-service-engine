package com.rd.credit.score.engine.serviceimpl;

import com.rd.credit.score.engine.entity.CreditScore;
import com.rd.credit.score.engine.repository.CreditScoreRepository;
import com.rd.credit.score.engine.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Autowired
    CreditScoreRepository creditScoreRepository;

    @Override
    public Integer checkCreditScore(String ssnNumber) {
        CreditScore creditScore = new CreditScore();
        creditScore = creditScoreRepository.findBySsnNumber(ssnNumber);
        if(creditScore!=null) {
            return getCreditScore(creditScore);

        }else{
            return 0;
        }
    }

	@Override
	public Integer getCreditScore(CreditScore creditScore) {

		if (creditScore.isCreditCardsBounce() == true && creditScore.isCheckBounce() == true
				&& creditScore.isLoanEmiBounce() == true) {
			return 100;
		} else if (creditScore.isCreditCardsBounce() == false && creditScore.isCheckBounce() == true
				&& creditScore.isLoanEmiBounce() == true) {
			return 300;
		} else if (creditScore.isCreditCardsBounce() == false && creditScore.isCheckBounce() == false
				&& creditScore.isLoanEmiBounce() == true) {
			return 500;
		} else if (creditScore.isCreditCardsBounce() == false && creditScore.isCheckBounce() == false
				&& creditScore.isLoanEmiBounce() == false) {
			return 750;
		}

		return 0;
	}
}
