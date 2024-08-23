package com.mybank.cardsservice.services.ServiceImpl;

import com.mybank.cardsservice.constants.CardsConstants;
import com.mybank.cardsservice.dto.ReadCardsDto;
import com.mybank.cardsservice.dto.RequestCardsDto;
import com.mybank.cardsservice.entity.Cards;
import com.mybank.cardsservice.exception.CardAlreadyExistsException;
import com.mybank.cardsservice.exception.ResourceNotFoundException;
import com.mybank.cardsservice.mapper.CardsMapper;
import com.mybank.cardsservice.repository.CardsRepository;
import com.mybank.cardsservice.services.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsMapper mapper;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public ReadCardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return mapper.CardsToReadCardDto(cards);
    }

    @Override
    public boolean updateCard(RequestCardsDto requestCardsDto) {
        Cards cards = cardsRepository.findByCardNumber(requestCardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", requestCardsDto.getCardNumber()));

        Cards updateCards = mapper.RequestCardDtoToCards(requestCardsDto);

        cardsRepository.save(updateCards);
        return  true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
