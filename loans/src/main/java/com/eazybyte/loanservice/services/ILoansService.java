package com.eazybyte.loanservice.services;

import com.eazybyte.loanservice.dto.ReadLoanDto;
import com.eazybyte.loanservice.dto.RequestLoanDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return
     */
    ReadLoanDto fetchLoan(String mobileNumber);

    /**
     *
     * @param requestLoanDto
     * @return
     */
    boolean updateLoan (RequestLoanDto requestLoanDto);

    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteLoan (String mobileNumber);

}
