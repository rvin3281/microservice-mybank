package com.arvend.accounts.service;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.constant.AccountType;
import com.arvend.accounts.dto.CreateAccountDto;
import com.arvend.accounts.dto.ReadAccountDto;
import com.arvend.accounts.dto.ReadCustomerAccountDto;
import com.arvend.accounts.entity.Accounts;
import com.arvend.accounts.entity.Customers;
import com.arvend.accounts.exception.AccountTypeException;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;
import com.arvend.accounts.exception.SaveException;
import com.arvend.accounts.mapper.AccountMapper;
import com.arvend.accounts.repository.AccountsRepository;
import com.arvend.accounts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {

    @Autowired
    AccountMapper mapper;

    @Autowired
    AccountsRepository repo;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public void createAccount (CreateAccountDto createAccountDto, String createBy)
            throws NotFoundException, InternalException, AccountTypeException {
        try{

            if (createAccountDto.getAccountType() == null ||
                    !(createAccountDto.getAccountType().equals(AccountType.SAVING) || createAccountDto.getAccountType().equals(AccountType.CURRENT))) {
                throw new AccountTypeException("Account Type only allow SAVING or CURRENT");
            }

            Accounts account = mapper.createAccountToAccount(createAccountDto);

            Optional<Customers> customer = customerRepository.findById(createAccountDto.getCustomer_id());

            if(customer.isEmpty()){
                throw new NotFoundException("Customer with ID "+createAccountDto.getCustomer_id()+" not found.");
            }

            Optional<Accounts> checkExistingAccount = repo.findById(createAccountDto.getAccountNumber());

            if(checkExistingAccount.isPresent())
            {
                throw new NotFoundException("Account Number Exist. Please try again");
            }

            account.setCreatedBy(createBy);
            account.setCustomer(customer.get());

            repo.save(account);

        }catch(NotFoundException | AccountTypeException | HttpMessageNotReadableException ex){
            throw ex;
        } catch(Exception ex){
            throw new InternalException(AccountConstant.MESSAGE_500);
        }

    }

    @Transactional(readOnly = true)
    public ReadCustomerAccountDto getCustomerWithAccount (String mobileNumber)
            throws InternalException, NotFoundException {
        try{

            Customers existingCustomer = customerRepository.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException(AccountConstant.MOBILE_NUMBER_NOT_FOUND_EXCEPTION)
            );

            int existingCustomerId = existingCustomer.getCustomer_id();

            List<Accounts> existingAccounts = repo.findByCustomerCustomerId(existingCustomerId);

            if(existingAccounts.isEmpty()){
                throw new NotFoundException("No account created for customer "+existingCustomer.getName());
            }

            List<ReadAccountDto> accountDto =  existingAccounts
                                            .stream()
                                            .map((account) -> mapper.accountToReadAccountDto(account))
                                            .toList();

            ReadCustomerAccountDto readCustomerAccountDto = new ReadCustomerAccountDto();

            readCustomerAccountDto.setCustomer_id(existingCustomerId);
            readCustomerAccountDto.setName(existingCustomer.getName());
            readCustomerAccountDto.setEmail(existingCustomer.getEmail());
            readCustomerAccountDto.setMobileNumber(existingCustomer.getMobileNumber());
            readCustomerAccountDto.setAccounts(accountDto);

            return readCustomerAccountDto;

        }catch(NotFoundException ex)
        {
            throw ex;

        }catch(Exception ex)
        {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }
    }

    @Transactional
    public void deleteAccountByCustomer (String mobileNumber, long accountNumber, String updateBy)
            throws InternalException, NotFoundException {
        try{

            // Find the customer by their mobile number.
            Customers existingCustomer = customerRepository.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException(AccountConstant.MOBILE_NUMBER_NOT_FOUND_EXCEPTION)
            );

            int existingCustomerId = existingCustomer.getCustomer_id();

            // Retrieve all accounts for that customer.
            List<Accounts> existingAccounts = repo.findByCustomerCustomerId(existingCustomerId);

            if(existingAccounts.isEmpty()){
                throw new NotFoundException("No account created for customer "+existingCustomer.getName());
            }

            // Find the matching account
            Optional<Accounts> existingAccount  =  existingAccounts
                                                    .stream()
                                                    .filter(account -> account.getAccountNumber() == accountNumber)
                                                    .findFirst();
            // If account is found, delete it
            if (existingAccount.isPresent()) {

                Accounts account = existingAccount.get();

                account.setIsActive(0);
                account.setUpdatedBy(updateBy);
                repo.save(account);

            } else {
                throw new NotFoundException("Account number " + accountNumber + " not found for customer " + existingCustomer.getName());
            }

        }catch(NotFoundException ex)
        {
            throw ex;
        }catch(Exception ex)
        {
            // Log the exception for debugging purposes
            // Logger can be used here to log the error
            // Logger.error("Error occurred while deleting account", ex);
            throw new InternalException(AccountConstant.MESSAGE_500);
        }
    }

}
