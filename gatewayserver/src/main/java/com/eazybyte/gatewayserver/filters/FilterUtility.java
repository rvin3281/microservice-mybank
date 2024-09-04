package com.eazybyte.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String CORELATION_ID = "eazybank-correlation-id";

    public String getCorrelationID(HttpHeaders requestHeaders){
        if(requestHeaders.get(CORELATION_ID) != null){
            List<String> requestHeaderList = requestHeaders.get(CORELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        }
        else{
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId){
        return this.setRequestHeader(exchange, CORELATION_ID, correlationId);
    }

}
