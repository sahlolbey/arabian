package com.fsp.arabian;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
