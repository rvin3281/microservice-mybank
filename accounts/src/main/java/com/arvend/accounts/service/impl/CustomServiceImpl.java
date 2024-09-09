package com.arvend.accounts.service.impl;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.dto.*;
import com.arvend.accounts.entity.Accounts;
import com.arvend.accounts.entity.Customers;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;
import com.arvend.accounts.mapper.AccountMapper;
import com.arvend.accounts.mapper.CustomerMapper;
import com.arvend.accounts.repository.AccountsRepository;
import com.arvend.accounts.repository.CustomerRepository;
import com.arvend.accounts.service.ICustomerService;
import com.arvend.accounts.service.client.CardsFeignClient;
import com.arvend.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomServiceImpl implements ICustomerService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CardsFeignClient cardsFeignClient;
    @Autowired
    private LoansFeignClient loansFeignClient;
    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public CustomerDetailsDto fetchFeignCustomerDetails(String mobileNumber, String correlationId)
            throws NotFoundException, InternalException {

        try{

            // GET CUSTOMER DETAILS
            Customers customer = customerRepository.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException("Customer with mobile number " + mobileNumber + " not found!")
            );

            // GET ACCOUNT DETAILS RELATED TO CUSTOMER
            int existingCustomerId = customer.getCustomer_id();

            List<Accounts> existingAccounts = accountsRepository.findByCustomerCustomerId(existingCustomerId);

            if(existingAccounts.isEmpty()){
                throw new NotFoundException("No account created for customer "+customer.getName());
            }

            List<ReadAccountDto> accountDto =  existingAccounts
                    .stream()
                    .map((account) -> accountMapper.accountToReadAccountDto(account))
                    .toList();

            ReadCustomerAccountDto readCustomerAccountDto = new ReadCustomerAccountDto();

            readCustomerAccountDto.setCustomer_id(existingCustomerId);
            readCustomerAccountDto.setName(customer.getName());
            readCustomerAccountDto.setEmail(customer.getEmail());
            readCustomerAccountDto.setMobileNumber(customer.getMobileNumber());
            readCustomerAccountDto.setAccounts(accountDto);

            CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
            customerDetailsDto.setReadCustomerAccountDto(readCustomerAccountDto);

            // FETCH LOAN DETAIL
           ResponseEntity<APIResponseWithDataDTO<ReadLoanDto>> loadDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);

           // RESILIENT CHECK => IF ANY ERROR ON LOAN SERVICE THEN IT RETURN NULL
            if(null != loadDtoResponseEntity){
                // GET THE LOAN INFORMATION
                customerDetailsDto.setReadLoanDto(loadDtoResponseEntity.getBody().getData());
            }

            // FETCH CArd DETAIL
            ResponseEntity<APIResponseWithDataDTO<ReadCardsDto>> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);

            // RESILIENT CHECK => IF ANY ERROR ON LOAN SERVICE THEN IT RETURN NULL
            if(null != cardDtoResponseEntity){
                // GET THE LOAN INFORMATION
                customerDetailsDto.setReadCardsDto(cardDtoResponseEntity.getBody().getData());
            }

            return customerDetailsDto;

        }catch(NotFoundException ex)
        {
            throw ex;

        }catch(Exception ex)
        {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }

    }


}
