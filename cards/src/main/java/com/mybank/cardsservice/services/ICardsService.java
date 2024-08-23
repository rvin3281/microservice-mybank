package com.mybank.cardsservice.services;

import com.mybank.cardsservice.dto.ReadCardsDto;
import com.mybank.cardsservice.dto.RequestCardsDto;

public interface ICardsService {

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createCard(String mobileNumber);

    /**
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    ReadCardsDto fetchCard(String mobileNumber);

    /**
     * @param requestCardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(RequestCardsDto requestCardsDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(String mobileNumber);

}
