package com.rd.credit.score.engine.repository;

import com.rd.credit.score.engine.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    CreditScore findBySsnNumber(String ssnNumber);
}
