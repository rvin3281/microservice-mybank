package com.arvend.accounts.constant;

import com.arvend.accounts.json.AccountTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AccountTypeDeserializer.class)
public enum AccountType {
    SAVING,
    CURRENT
}
