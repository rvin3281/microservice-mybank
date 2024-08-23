package com.arvend.accounts.json;

import com.arvend.accounts.constant.AccountType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;

public class AccountTypeDeserializer extends JsonDeserializer<AccountType> {

    @Override
    public AccountType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase(); // Convert to uppercase to handle case-insensitive values
        try {
            return AccountType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw JsonMappingException.from(p, "Invalid account type: " + value + ". Allowed values are: SAVING, CURRENT");
        }
    }
}
