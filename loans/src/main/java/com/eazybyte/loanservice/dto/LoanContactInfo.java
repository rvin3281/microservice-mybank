package com.eazybyte.loanservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="loans")
@Data
@Getter
@Setter
public class LoanContactInfo {

    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;

}
