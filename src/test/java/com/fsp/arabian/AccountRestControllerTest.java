package com.fsp.arabian;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsp.arabian.model.Account;
import org.hibernate.engine.jdbc.env.spi.IdentifierCaseStrategy;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountRestControllerTest {
    @Test
    public void testTransfer(){
        String resourceUrl
                = "http://localhost:8080/transfer";
        Map<String, String> params = new HashMap<String, String> ();
        params.put("source", "1234");
        params.put("destination", "1234");
        params.put("amount", "1234");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response =
                restTemplate.postForEntity( resourceUrl, params,
                Integer.class );
        try {
            Thread.sleep (1000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

}
