package com.arvend.accounts.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="accounts")
@Data
@Getter
@Setter
public class AccountContactInfoDto {

    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;



}
