package com.eazybyte.loanservice.services.ServicesImpl;

import com.eazybyte.loanservice.constant.LoansConstants;
import com.eazybyte.loanservice.dto.ReadLoanDto;
import com.eazybyte.loanservice.dto.RequestLoanDto;
import com.eazybyte.loanservice.entity.Loans;
import com.eazybyte.loanservice.exception.LoanAlreadyExistsException;
import com.eazybyte.loanservice.exception.ResourceNotFoundException;
import com.eazybyte.loanservice.mapper.LoansMapper;
import com.eazybyte.loanservice.repository.LoansRepository;
import com.eazybyte.loanservice.services.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements ILoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansMapper mapper;

    /**
     *
     * @param mobileNumber

     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException(
                    "Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));

    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    private Loans createNewLoan(String mobileNumber){
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public ReadLoanDto fetchLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return mapper.LoansToReadLoanDto(loans);

    }

    @Override
    public boolean updateLoan(RequestLoanDto requestLoanDto) {

        Loans loans = loansRepository.findByLoanNumber(requestLoanDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", requestLoanDto.getLoanNumber())
        );
        mapper.LoansToReadLoanDto(loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;

    }

}
