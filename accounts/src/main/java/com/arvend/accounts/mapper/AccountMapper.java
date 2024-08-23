package com.arvend.accounts.mapper;

import com.arvend.accounts.dto.CreateAccountDto;
import com.arvend.accounts.dto.ReadAccountDto;
import com.arvend.accounts.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Accounts createAccountToAccount (CreateAccountDto createAccountDto);

    ReadAccountDto accountToReadAccountDto (Accounts account);

}

