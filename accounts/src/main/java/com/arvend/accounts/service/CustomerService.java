package com.arvend.accounts.service;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.dto.CreateCustomerDto;
import com.arvend.accounts.dto.ReadCustomerDto;
import com.arvend.accounts.dto.UpdateCustomerDto;
import com.arvend.accounts.entity.Customers;
import com.arvend.accounts.exception.*;
import com.arvend.accounts.mapper.CustomerMapper;
import com.arvend.accounts.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private CustomerMapper mapper;

//    @PostConstruct
//    public void initDB()
//    {
//        List<Customers> customer = IntStream.rangeClosed(1,10000)
//                .mapToObj(i -> new Customers("test "+i, "test"+i+"@cc.com","012518515"+i,"arvend"+i,1))
//                .collect(Collectors.toList());
//        repo.saveAll(customer);
//    }

    /**
     * @param createCustomerDto
     * @throws InternalException
     * @throws SaveException
     */
    public void createCustomer(CreateCustomerDto createCustomerDto) throws InternalException, SaveException {

        try {
            Customers customers = mapper.createCustomerDtoToCustomer(createCustomerDto);

            /** VALIDATE WHETHER CUSTOMER MOBILE NUMBER EXIST OR NOT **/
            Optional<Customers> optionalCustomer = repo.findByMobileNumberAndIsActive(createCustomerDto.getMobileNumber());
            if (optionalCustomer.isPresent()) {
                throw new CustomerAlreadyExistsException("Customer already registered with given MobileNumber "
                        + createCustomerDto.getMobileNumber());
            }

            if (repo.save(customers) == null)
                throw new SaveException("Failed to Save Customer");
        } catch (SaveException | CustomerAlreadyExistsException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }
    }

    /**
     * @return
     * @throws InternalException
     */
    @Transactional(readOnly = true)
    public List<ReadCustomerDto> getAllCustomers() throws InternalException {
        try {
            List<Customers> customers = repo.findAllActiveCustomers();

            return customers.stream()
                    .map((customer) -> mapper.customerToReadCustomerDto(customer))
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }

    }

    /**
     * @param mobileNumber
     * @return
     * @throws NotFoundException
     * @throws InternalException
     */
    public ReadCustomerDto getCustomerByMobileNumber(String mobileNumber) throws NotFoundException, InternalException {

        try {

            Customers customer = repo.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException("Customer with mobile number " + mobileNumber + " not found!")
            );

            return mapper.customerToReadCustomerDto(customer);

        } catch (Exception ex) {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }

    }

    /**
     * @param mobileNumber
     * @param updateCustomerDto
     * @return
     * @throws InternalException
     * @throws IDNotMatchException
     * @throws NotFoundException
     */
    @Transactional
    public ReadCustomerDto updateCustomerByMobileNumber(String mobileNumber, String updateBy, UpdateCustomerDto updateCustomerDto)
            throws InternalException, IDNotMatchException, NotFoundException {
        try {

            Customers existingCustomer = repo.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException("Customer with mobile number " + mobileNumber + " not found!")
            );

            int customerID = existingCustomer.getCustomer_id();

            if (customerID != updateCustomerDto.getCustomer_id()) {
                throw new IDNotMatchException("Customer ID " + customerID + " not matched. Please try again");
            }

            if (updateCustomerDto.getMobileNumber() != null && !Objects.equals(updateCustomerDto.getMobileNumber(), existingCustomer.getMobileNumber())) {
                existingCustomer.setMobileNumber(updateCustomerDto.getMobileNumber());
            }

            if (updateCustomerDto.getName() != null && !Objects.equals(updateCustomerDto.getName(), existingCustomer.getName())) {
                existingCustomer.setName(updateCustomerDto.getName());
            }

            if (updateCustomerDto.getEmail() != null && !Objects.equals(updateCustomerDto.getEmail(), existingCustomer.getEmail())) {
                existingCustomer.setEmail(updateCustomerDto.getEmail());
            }

            if (updateCustomerDto.getName() != null && !Objects.equals(updateCustomerDto.getName(), existingCustomer.getName())) {
                existingCustomer.setName(updateCustomerDto.getName());
            }

            Customers updatedCustomer = repo.save(existingCustomer);

            // Add who update the Customer Detail
            updatedCustomer.setUpdatedBy(updateBy);

            return (mapper.customerToReadCustomerDto(updatedCustomer));


        } catch (NotFoundException | IDNotMatchException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalException(AccountConstant.MESSAGE_500);
        }
    }

    @Transactional
    public void deleteCustomerByMobileNumber(String mobileNumber, String updateBy)
            throws NotFoundException, InternalException {

        try{

            Customers existingCustomer = repo.findByMobileNumberAndIsActive(mobileNumber).orElseThrow(
                    () -> new NotFoundException("Customer with mobile number " + mobileNumber + " not found!")
            );

            existingCustomer.setUpdatedBy(updateBy);
            existingCustomer.setIsActive(0);

            repo.save(existingCustomer);


        }catch(NotFoundException ex){
            throw ex;
        }catch(Exception ex){
            throw new InternalException(AccountConstant.MESSAGE_500);
        }
    }

}
