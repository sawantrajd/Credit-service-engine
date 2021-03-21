package com.rd.credit.score.engine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "credit_score")
public class CreditScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_CreditScore")
    private Long idCreditScore;
    private String ssnNumber;
    private boolean checkBounce;
    private boolean creditCardsBounce;
    private boolean loanEmiBounce;
}
