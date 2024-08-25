package com.arvend.accounts.service;

import com.arvend.accounts.dto.CustomerDetailsDto;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDetailsDto fetchFeignCustomerDetails(String mobileNumber) throws NotFoundException, InternalException;

}
