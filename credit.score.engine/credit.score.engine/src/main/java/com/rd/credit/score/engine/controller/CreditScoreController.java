package com.rd.credit.score.engine.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rd.credit.score.engine.service.CreditScoreService;

@RestController
public class CreditScoreController {

    @Autowired
    CreditScoreService creditScoreService;

    @GetMapping("/credit")
    public Integer checkCreditScore(@RequestParam @Valid String ssnNumber) {
        return creditScoreService.checkCreditScore(ssnNumber);
    }
}
