package com.eazybyte.loanservice.mapper;

import com.eazybyte.loanservice.dto.ReadLoanDto;
import com.eazybyte.loanservice.dto.RequestLoanDto;
import com.eazybyte.loanservice.entity.Loans;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoansMapper {


    Loans RequestLoanDtoToLoan(RequestLoanDto requestLoanDto);

    ReadLoanDto LoansToReadLoanDto (Loans loans);

}

