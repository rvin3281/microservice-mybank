package com.mybank.cardsservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix="cards")
@Data
@Getter
@Setter
public class CardsContactInfo{

    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;
}
