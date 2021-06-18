package com.microservice.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private Environment enviornment;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")//exposing url
    public CurrencyExchange retrieveExchangeValue (@PathVariable String from, @PathVariable String to){

        logger.info("retrieveExchangeValue called with {} to {}", from, to);
        //CurrencyExchange currencyExchange = new CurrencyExchange(1000L,from, to, BigDecimal.valueOf(50));
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException("unable to find data for " + from + "from, to" + to);
        }
        String port = enviornment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
