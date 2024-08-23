package com.mybank.cardsservice.mapper;

import com.mybank.cardsservice.dto.ReadCardsDto;
import com.mybank.cardsservice.dto.RequestCardsDto;
import com.mybank.cardsservice.entity.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardsMapper {

    CardsMapper INSTANCE = Mappers.getMapper(CardsMapper.class);

    Cards RequestCardDtoToCards (RequestCardsDto requestCardsDto);

    ReadCardsDto CardsToReadCardDto (Cards card);

}
